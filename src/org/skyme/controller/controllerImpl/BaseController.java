package org.skyme.controller.controllerImpl;

import org.skyme.config.ServletConfig;
import org.skyme.controller.Controller;
import org.skyme.core.Request;
import org.skyme.core.Response;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author:Skyme
 * @create: 2023-09-09 17:53
 * @Description:
 */
public class BaseController implements Controller {
    @Override
    public void init() {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(Request request, Response response) {
        String uri = request.getUri();
        String method = uri.substring(uri.lastIndexOf("/") + 1);

        try {
            System.out.println(method);
            Method method1 = getClass().getMethod(method, Request.class, Response.class);
            method1.setAccessible(true);
            method1.invoke(this,request, response);
        } catch (NoSuchMethodException e) {
          response.responseNotFound();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
