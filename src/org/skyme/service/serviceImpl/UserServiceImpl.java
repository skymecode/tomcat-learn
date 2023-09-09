package org.skyme.service.serviceImpl;

import org.skyme.core.Request;
import org.skyme.core.Response;
import org.skyme.dao.jdbc.SqlUtil;
import org.skyme.service.UserService;
import org.skyme.entity.User;
import org.skyme.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author:Skyme
 * @create: 2023-09-09 18:54
 * @Description:
 */
public class UserServiceImpl implements UserService {
    @Override
    public void login(Request request, Response response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        List<User> user = SqlUtil.select(User.class, "select * from qq_user where username=?", username);
        if(user == null||user.size() == 0){
            response.setMessage("用户不存在");
            response.response();
            return;
        }else{
            String md5Str = null;
            try {
                md5Str = MD5Util.getMD5Str(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            if(!user.get(0).getPassword().equals(md5Str)){
                response.setMessage("密码错误");
                response.response();
                return;
            }
        }
        response.setMessage("登录成功");
        response.response();
    }
}
