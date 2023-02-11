package com.cs.mvc.servlet;

import com.alibaba.fastjson.JSON;
import com.cs.ioc.utils.CodecUtil;
import com.cs.ioc.utils.JSONUtils;
import com.cs.ioc.utils.ReflectionUtils;
import com.cs.mvc.annotation.ResponseBody;
import com.cs.mvc.bean.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<Request, RequestHandler> mappingMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化 IOC容器
        ServletContext servletContext = config.getServletContext();
        Object map = servletContext.getAttribute("mappingMap");
        mappingMap = (Map<Request, RequestHandler>) map;

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求方法与请求的路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();

        doService(req,resp,requestMethod, requestPath);
    }

    public Map<Request, RequestHandler> getMappingMap() {
        return mappingMap;
    }

    private void doService(HttpServletRequest req, HttpServletResponse resp, String requestMethod, String requestPath) throws IOException, ServletException {
        RequestMethod method = null;
        if (requestMethod.equals("GET")) {
            method = RequestMethod.GET;
        } else {
            method = RequestMethod.POST;

        }
        Request request = new Request(method, requestPath);
        RequestHandler handler = mappingMap.get(request);

        if (handler != null) {
//            创建请求参数的对象 get请求
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            String body = null;
            try {
                body = CodecUtil.decodeURL(JSONUtils.getString(req.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (body != null && !"".equals(body.trim())) {
                String[] params = body.substring(body.indexOf("?")).split("&");
                if (params != null) {
                    for (String param : params) {
                        String[] array = param.split("=");
                        String paramName = array[0];
                        String paramValue = array[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
            Param param = new Param(paramMap);

            ModelAndView result =(ModelAndView) ReflectionUtils.invokeMappingMethod(handler.getMethod(), handler.getControllerClass(), param);

            Map<String, Object> model = result.getModel();
            if ("json".equals(result.getViewName()) ||handler.getMethod().isAnnotationPresent(ResponseBody.class)) {
                if (!model.isEmpty()) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JSON.toJSONString(model);
                    writer.write(json);
                    writer.flush();
                }
            } else {
                String url = "/" + result.getPath() + "." + result.getViewName();
                for (String key : model.keySet()) {
                    req.setAttribute(key,model.get(key));
                }
                req.getRequestDispatcher(url).forward(req, resp);
            }

        }
    }
}
