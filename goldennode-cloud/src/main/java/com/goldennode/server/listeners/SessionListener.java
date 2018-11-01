package com.goldennode.server.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		//sessionEvent.getSession().setMaxInactiveInterval(Integer.parseInt(sessionEvent.getSession().getServletContext()
		//		.getInitParameter(
		//				"MAX_INACTIVE_INTERVAL")));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		
			//
			
		
	}
}
