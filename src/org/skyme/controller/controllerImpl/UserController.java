package org.skyme.controller.controllerImpl;

import org.skyme.config.ServletConfig;
import org.skyme.controller.Controller;
import org.skyme.core.Request;
import org.skyme.core.Response;
import org.skyme.service.UserService;
import org.skyme.service.serviceImpl.UserServiceImpl;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:40
 * @Description: 用户控制器
 */
public class UserController extends BaseController {
   UserService userService=new UserServiceImpl();
   public void login(Request request,Response response){

       userService.login(request,response);

   }
}
