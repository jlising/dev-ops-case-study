package com.ibm.ph.edm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */
//https://samerabdelkafi.wordpress.com/2014/08/03/spring-mvc-full-java-based-config/
public class AppInitializer implements WebApplicationInitializer {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
	
	public void onStartup(ServletContext container) throws ServletException {
		LOG.debug("Starting up web container: container={}", container);
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		
		//Scan configuration classes annotated with @Configuration
        //Beware of precedence
		context.scan("com.ibm.ph.edm.config");
		context.scan("com.ibm.ph.edm.**.config");
		//context.register(); -- to manually load config classes

		container.addListener(new ContextLoaderListener(context));

		//Integrate security layer
        //Security config is separated to avoid conflict in orders
		FilterRegistration.Dynamic springSecFilter = container.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		springSecFilter.setAsyncSupported(true);

		springSecFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), true, "/*");

		//MVC dispatcher
		ServletRegistration.Dynamic mvcDispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));
		mvcDispatcher.setLoadOnStartup(1);
		mvcDispatcher.addMapping("/");
	}
}
