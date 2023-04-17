package org.example.filter;

import javax.servlet.*;
import java.io.IOException;

public class Filter03 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("C");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("C2");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
