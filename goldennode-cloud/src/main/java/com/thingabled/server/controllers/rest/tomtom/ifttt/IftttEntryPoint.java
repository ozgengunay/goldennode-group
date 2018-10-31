package com.thingabled.server.controllers.rest.tomtom.ifttt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;


public class IftttEntryPoint extends OAuth2AuthenticationEntryPoint {

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		String jsonObject = "{\"errors\": [{\"message\": \"" + "Invalid authentication" + "\"}]}";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		out.flush();
		out.close();
	}

}
