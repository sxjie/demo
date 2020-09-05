package com.hexin.common.filter;

import com.alibaba.fastjson.JSON;
import com.hexin.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Scanner;

/**
 * 权限过滤器
 */
public class PermissionsAuthFilter implements Filter {

    private static final String FIND_API = "/userList"; //   tokenTest/userList

    private static final Logger log = LoggerFactory.getLogger(PermissionsAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("::::::::::::::::::::: 请求访问权限过滤器初始化 :::::::::::::::::::::");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("::::::::::::::::::::: doFilter 方法被执行执行 :::::::::::::::::::::");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // 获取当前请求路径是否有权限
        String servletPath = httpRequest.getServletPath();
        if (!isExcludePath(servletPath)) {
            //servletResponse.getWriter().write("没有访问权限");
            log.info(">> >> >> >> >> : 没有访问权限");
        } else {
            log.info(">> >> >> >> >> : 请求成功");
        }
        log.info("当前请求路径:" + servletPath);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("::::::::::::::::::::: 请求访问权限过滤器销毁 :::::::::::::::::::::");
    }

    // 效验登陆路径
    private boolean isExcludePath(String path) {
        return FIND_API.equals(path);
    }



}
