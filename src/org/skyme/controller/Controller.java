package org.skyme.controller;

import org.skyme.config.ServletConfig;
import org.skyme.core.Request;
import org.skyme.core.Response;

public interface Controller {
    public void init();

    public ServletConfig getServletConfig();

    public void service(Request request, Response response);

    public String getServletInfo();

    public void destroy();
}
