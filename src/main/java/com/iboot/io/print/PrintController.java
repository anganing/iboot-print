package com.iboot.io.print;

import cn.hutool.core.text.CharSequenceUtil;
import com.iboot.io.print.core.GenHtmlDto;
import com.iboot.io.print.core.HiprintRenderEngine;
import org.noear.solon.annotation.*;

import java.util.Objects;

/**
 * 打印控制器类，处理打印相关的 HTTP 请求。
 *
 * <p>该控制器提供了生成打印预览 HTML 和获取 Hiprint 版本等功能的 Web API 接口。
 * 所有的打印相关请求都会由此控制器进行处理和响应。</p>
 *
 * @author tangsc
 * @since 2025-05-13 10:27
 */
@Controller
public class PrintController {
    @Inject
    private HiprintRenderEngine hiprintRenderEngine;

    /**
     * 生成打印预览 HTML。
     *
     * <p>接收打印模板和数据，生成可用于预览的 HTML 内容。该方法会对输入参数进行验证，
     * 确保必要的数据都已提供。</p>
     *
     * @param genHtmlDto 包含模板内容和打印数据的 DTO 对象
     * @return 生成的 HTML 字符串
     * @throws IllegalArgumentException 当请求参数为空或必要字段缺失时
     */
    @Post
    @Mapping("/generateHtml")
    public String generateHtml(@Body GenHtmlDto genHtmlDto) {
        if (Objects.isNull(genHtmlDto)) {
            throw new IllegalArgumentException("请求参数不能为空");
        }
        String templateContent = genHtmlDto.templateContent();
        String printData = genHtmlDto.printData();
        if (CharSequenceUtil.isEmpty(templateContent)) {
            throw new IllegalArgumentException("模板内容不能为空");
        }
        if (CharSequenceUtil.isEmpty(templateContent)) {
            throw new IllegalArgumentException("打印数据不能为空");
        }
        return hiprintRenderEngine.generateHtml(templateContent, printData);
    }

    /**
     * 获取 Hiprint 版本信息。
     *
     * @return Hiprint 版本号字符串
     */
    @Get
    @Mapping("/getHiprintVersion")
    public String getHiprintVersion() {
        return hiprintRenderEngine.getHiprintVersion();
    }
}