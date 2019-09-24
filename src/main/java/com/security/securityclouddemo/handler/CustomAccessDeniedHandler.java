package com.security.securityclouddemo.handler;

import com.security.securityclouddemo.common.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 授权失败时返回的信息
     * @param httpServletRequest request
     * @param httpServletResponse Response
     * @param e e
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        Result result = new Result(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "未授权");
        httpServletResponse.getWriter().println(result.toString());
        httpServletResponse.getWriter().flush();
    }
}
