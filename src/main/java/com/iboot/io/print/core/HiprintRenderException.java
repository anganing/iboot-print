package com.iboot.io.print.core;

/**
 * Hiprint 渲染异常类。
 *
 * <p>在 Hiprint 模板渲染过程中发生的异常会被封装为此类型。
 * 继承自 {@link RuntimeException}，属于非受检异常。</p>
 *
 * @author tangsc
 * @since 2025-05-13 9:27
 */
public class HiprintRenderException extends RuntimeException {

    /**
     * 使用指定的错误消息构造异常。
     *
     * @param message 详细的错误消息
     */
    public HiprintRenderException(String message) {
        super(message);
    }

    /**
     * 使用指定的错误消息和原因构造异常。
     *
     * @param message 详细的错误消息
     * @param cause 导致此异常的原因
     */
    public HiprintRenderException(String message, Throwable cause) {
        super(message, cause);
    }
}