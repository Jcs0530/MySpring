package com.cs.mvc.listen;

import com.cs.ioc.factory.ApplicationContext;
import com.cs.ioc.utils.Constant;
import com.cs.mvc.bean.Request;
import com.cs.mvc.bean.RequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.util.Map;

public class ContextLoaderListener implements ServletContextListener {
    public static final String LOCATION = "contextConfigLocation";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        String contextConfig = servletContext.getInitParameter(LOCATION);
//        System.out.println(contextConfig);
//        System.out.println(contextConfig.split(":")[1]);
        ApplicationContext applicationContext = new ApplicationContext(contextConfig.split(":")[1]);
        Map<Request, RequestHandler> mappingMap = applicationContext.getMappingMap();
        servletContext.setAttribute("mappingMap", mappingMap);

        //注册处理jsp的servlet

        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");

        String jspUrl = applicationContext.getProperties().getProperty(Constant.JSP_PATH);

        jspServlet.addMapping(jspUrl+"*");

//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(applicationContext.getProperties().getProperty(Constant.ASSET_PATH));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
