package com.iboot.io.print.core;

import cn.hutool.core.io.FileUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Hiprint 服务端渲染引擎。
 * 
 * <p>该类负责将 Hiprint 模板和数据在服务端进行渲染，生成 HTML 或获取版本信息。
 * 使用 HtmlUnit 模拟浏览器环境执行 JavaScript 代码。</p>
 *
 * @author tangsc
 * @since 2025-05-13 8:49
 */
@Slf4j
@Component
public class HiprintRenderEngine {
    /** 服务器端口号，默认为 8080。自动更新仅适用于单例模式。 */
    @Inject(value = "${server.port:8080}", autoRefreshed = true)
    private int serverPort;

    /** 服务器上下文路径，默认为根路径 "/"。 */
    @Inject(value = "${server.contextPath:/}", autoRefreshed = true)
    private String serverContextPath;

    /**
     * 生成打印预览 HTML。
     *
     * @param templateContent Hiprint 模板内容（JSON 格式）
     * @param printData 打印数据（JSON 格式）
     * @return 渲染后的 HTML 字符串
     * @throws HiprintRenderException 如果渲染过程中发生错误
     */
    public String generateHtml(String templateContent, String printData) {
        // 对模板内容进行 base64 编码，避免控制字符导致 JSON.parse 失败
        String base64Template = Base64.getEncoder().encodeToString(templateContent.getBytes(StandardCharsets.UTF_8));
        String base64PrintData = Base64.getEncoder().encodeToString(printData.getBytes(StandardCharsets.UTF_8));

        String script = """
                (function() {
                    try {
                        var decoder = new TextDecoder('utf-8');
                        var tplBase64 = atob('%s');
                        var dataBase64 = atob('%s');
                        var tplBytes = new Uint8Array(tplBase64.length);
                        for (var i = 0; i < tplBase64.length; i++) {
                            tplBytes[i] = tplBase64.charCodeAt(i);
                        }
                        var dataBytes = new Uint8Array(dataBase64.length);
                        for (var i = 0; i < dataBase64.length; i++) {
                            dataBytes[i] = dataBase64.charCodeAt(i);
                        }
                        var tpl = decoder.decode(tplBytes);
                        var data = decoder.decode(dataBytes);
                        var result = generateHtml(tpl, data);
                        return result;
                    } catch(e) {
                        return 'ERROR: ' + e.message;
                    }
                })()
                """.formatted(base64Template, base64PrintData);

        String tempHtml = executeHiprintScript(script);

        //========== 构建最终 HTML ==========//
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset='UTF-8'>
                  <style>
                %s
                  </style>
                </head>
                <body>
                %s
                </body>
                </html>
                """.formatted(
                FileUtil.readUtf8String("static/hiprint/css/print-lock.css"),
                tempHtml
        );
    }

    /**
     * 获取 Hiprint 版本号。
     *
     * @return Hiprint 版本号字符串
     * @throws HiprintRenderException 如果获取版本号过程中发生错误
     */
    public String getHiprintVersion() {
        String script = """
                (function() {
                    try {
                        var result = getHiprintVersion();
                        return result;
                    } catch(e) {
                        return 'ERROR: ' + e.message;
                    }
                })()
                """;
        return executeHiprintScript(script);
    }

    /**
     * 执行 Hiprint JavaScript 脚本。
     *
     * @param script 要执行的 JavaScript 脚本
     * @return 脚本执行结果
     * @throws HiprintRenderException 如果脚本执行过程中发生错误
     */
    private String executeHiprintScript(String script) {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            //========== 浏览器配置 ==========//
            webClient.getOptions().setJavaScriptEnabled(true);       // 必须启用 JS
            webClient.getOptions().setCssEnabled(false);             // 禁用 CSS 解析（按需开启）
            webClient.getOptions().setThrowExceptionOnScriptError(false); // 忽略脚本错误
            webClient.getOptions().setGeolocationEnabled(false);      // 禁用地理定位
            webClient.getOptions().setAppletEnabled(false);           // 禁用 Applet
            webClient.setJavaScriptTimeout(15000);                   // JS 执行超时时间（毫秒）
            webClient.getCookieManager().setCookiesEnabled(true);     // 启用 Cookie

            //========== 构建请求 URL ==========//
            String hiprintEntry = getHiprintEntry();

            //========== 页面加载 ==========//
            HtmlPage page = webClient.getPage(hiprintEntry);

            // 等待 JS 初始化完成（关键！）
            webClient.waitForBackgroundJavaScript(3000);
            int waitCount = 0;
            while (waitCount++ < 5) {
                Thread.sleep(500);  // 防止 CPU 过载
            }

            //========== 执行核心脚本 ==========//
            log.debug("执行的 script = {}", script);
            ScriptResult result = page.executeJavaScript(script);
            String scriptResult = (String) result.getJavaScriptResult();

            //========== 异常处理 ==========//
            if (scriptResult.startsWith("ERROR:")) {
                throw new HiprintRenderException("JavaScript 执行失败: " + scriptResult.substring(6));
            }
            return scriptResult;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new HiprintRenderException("Hiprint 脚本执行被中断", e);
        } catch (Exception e) {
            throw new HiprintRenderException("Hiprint 脚本执行失败", e);
        }
    }

    /**
     * 获取 Hiprint 入口页面 URL。
     *
     * @return 完整的入口页面 URL
     */
    private String getHiprintEntry() {
        return String.format("http://localhost:%s%sindex.html",serverPort, serverContextPath);
    }
}
