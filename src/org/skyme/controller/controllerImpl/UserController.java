package org.skyme.controller.controllerImpl;

import org.skyme.config.ServletConfig;
import org.skyme.controller.Controller;
import org.skyme.core.Request;
import org.skyme.core.Response;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:40
 * @Description: 用户控制器
 */
public class UserController extends BaseController {
   public void login(Request request,Response response){
        response.setMessage("<html><head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>登录</title>\n" +
                "</head><body><h1>这个是登录的方法</h1></body></html>");
        response.response();

   }
}
