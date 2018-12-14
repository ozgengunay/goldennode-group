package com.goldennode.server.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class GoldenNodeAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String jsonObject = "{\"errors\": [{\"message\": \"" + "Invalid authentication" + "\"}]}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(javax.servlet.http.HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }
}