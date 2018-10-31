package com.thingabled.server.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebListener
public class ContextListener implements ServletContextListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		
		LOGGER.debug("Context Destroyed... / "
				+ contextEvent.getServletContext().getRealPath("/") + " / "
				+ contextEvent.getServletContext().getServletContextName());
		
	}


	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			
			//WebApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(contextEvent.getServletContext());
			
			LOGGER.debug("Context initialized... / "
					+ contextEvent.getServletContext().getRealPath("/") + " / "
					+ contextEvent.getServletContext().getServletContextName());
		} catch (Exception e) {
			LOGGER.error("error initializing context",e);
		}
	}

	
}
