package com.goldennode.server.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class GoldenNodeAccessDeniedHandler implements  AccessDeniedHandler {

	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException reason)
			throws ServletException, IOException {

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