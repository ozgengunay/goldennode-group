package com.thingabled.server.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletCommonVariableGettersSetters {
	public static Object getContextAttribute(HttpSession session,
			String attributeName) {
		return ServletCommonVariableGettersSetters.getContextAttribute(
				session.getServletContext(), attributeName);
	}

	public static Object getContextAttribute(ServletContext context,
			String attributeName) {
		return context.getAttribute(attributeName);
		
	}

	public static String getCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() != null) {
			for (int i = 0; i < request.getCookies().length; i++) {
				Cookie cookie = request.getCookies()[i];
				if (cookieName.equals(cookie.getName())) {
					String value=null;
					try {
						value=URLDecoder.decode(cookie.getValue(),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						//
					}  
					return (value);
				}
			}
		}
		return null;
	}

	public static Object getSessionAttribute(HttpSession session,
			String attributeName) {
		return session.getAttribute(attributeName);
		
	}

	public static void setContextAttribute(HttpSession session,
			String attributeName, Object attributeValue) {
		ServletCommonVariableGettersSetters.setContextAttribute(
				session.getServletContext(), attributeName, attributeValue);
	}

	public static void setContextAttribute(ServletContext context,
			String attributeName, Object attributeValue) {
		context.setAttribute(attributeName, attributeValue);
		
	}

	public static void setCookie(HttpServletResponse response,
			String cookieName, String value) {
		try {
			value=URLEncoder.encode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			//
		}  
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setMaxAge(1 * 3600 * 24 * 365 * 10);
		response.addCookie(cookie);
	}

	public static void setRequestAttribute(HttpServletRequest request,
			String attributeName, String attributeValue) {
		request.setAttribute(attributeName, attributeValue);
	}

	public static void setSessionAttribute(HttpSession session,
			String attributeName, Object attributeValue) {
		session.setAttribute(attributeName, attributeValue);
		
	}
	
	
	
	
}
