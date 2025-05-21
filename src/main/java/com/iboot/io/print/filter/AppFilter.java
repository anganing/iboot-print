package com.iboot.io.print.filter;

import com.iboot.io.print.core.R;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * index 为顺序位（不加，则默认为0）
 */
@Slf4j
@Component(index = 0)
public class AppFilter implements Filter {
    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(ctx);
        } catch (Throwable e){
            String detailMsg;
            try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                detailMsg = sw.toString();
            }
            log.error(detailMsg);
            ctx.render(R.fail(e.getMessage(), detailMsg));
        }
    }
}