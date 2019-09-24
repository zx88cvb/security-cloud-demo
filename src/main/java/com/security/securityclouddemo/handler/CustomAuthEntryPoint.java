package com.security.securityclouddemo.handler;

import com.security.securityclouddemo.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * 自定义EntryPoint用于tokan校验失败返回信息
     * @param httpServletRequest request
     * @param httpServletResponse Response
     * @param e e
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        Result result = new Result(String.valueOf(HttpServletResponse.SC_BAD_REQUEST), "token校验失败");
        httpServletResponse.getWriter().print(result.toString());
        httpServletResponse.getWriter().flush();
    }
}
