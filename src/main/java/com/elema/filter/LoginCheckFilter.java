package com.elema.filter;

import com.alibaba.fastjson.JSON;
import com.elema.common.Results;
import com.elema.common.ThreadContext;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")// 设置拦截路径
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        // 获取本次请求
        String requestURI = request.getRequestURI();

        // 判断请求是否在不处理的请求范围之内,使用路径匹配器进行判断
        boolean check = check(urls, requestURI);
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }

        // 判断员工是否已经登录
        if (request.getSession().getAttribute("employee") != null){
            // 将当前用户id存入ThreadLocal
            ThreadContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        //4-2、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            ThreadContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        // 没办法return 使用response输出信息
        response.getWriter().write(JSON.toJSONString(Results.error("NOTLOGIN")));// 前端接受格式
    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
