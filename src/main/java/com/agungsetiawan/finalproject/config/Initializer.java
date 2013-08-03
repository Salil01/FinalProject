package com.agungsetiawan.finalproject.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author awanlabs
 */
public class Initializer implements WebApplicationInitializer{
    
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
        ctx.register(WebAppConfig.class);
        sc.addListener(new ContextLoaderListener(ctx));
        ctx.setServletContext(sc);
        
        ServletRegistration.Dynamic servlet=sc.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        
    }
    
}
