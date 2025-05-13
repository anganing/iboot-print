package com.iboot.io.print;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;

/**
 * 打印服务应用程序入口类。
 *
 * <p>该类使用 Solon 框架作为应用程序的启动入口，负责初始化和启动整个打印服务。
 * 通过 {@link SolonMain} 注解标记为主程序入口。</p>
 *
 * @author tangsc
 * @since 2025-05-12 9:27
 */
@SolonMain
public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args);
    }
}