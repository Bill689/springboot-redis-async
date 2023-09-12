package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "tokenFilter",urlPatterns = "/secure/*")
public class TokenFilter implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.ttl}")
    private Long tokenTTL;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setCharacterEncoding("UTF-8");
        //获取header里的token
        String token=request.getHeader("token");
        if ("OPTIONS".equals(request.getMethod())) {              //除了 OPTIONS请求以外, 其它请求应该被JWT检查
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        }else {
            if (token == null) {
                response.getWriter().write("没有token！");
                return;
            }
        }

        Map<Object, Object> userMap = redisUtil.hmget(token);
        if(userMap.isEmpty()){
            response.getWriter().write("token过期请重新登录！");
        }else{
            redisUtil.expire(token,tokenTTL);   //刷新过期时间
            Integer id = (Integer)userMap.get("id");
            String name = (String)userMap.get("name");
            String userName = (String)userMap.get("userName");
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            request.setAttribute("name", name);
            request.setAttribute("userName", userName);
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
