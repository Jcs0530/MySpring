package com.xiaoqiang.mvc.listen;

import com.xiaoqiang.ioc.factory.ApplicationContext;
import com.xiaoqiang.ioc.utils.Constant;
import com.xiaoqiang.mvc.bean.Request;
import com.xiaoqiang.mvc.bean.RequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/10/7-21:36
 */
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
