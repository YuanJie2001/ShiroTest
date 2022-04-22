package com.vector.springbootshiro.controller;

import com.vector.springbootshiro.Utils.Result;
import com.vector.springbootshiro.entity.User;
import com.vector.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName ShiroController
 * @date 2022/4/17 17:03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // 用户注册
    @PostMapping("/register")
    public Object register(User user) {
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>().error(500,"服务器内部错误");
        }
        return new Result<>().success(200,"注册成功");

    }


    @PostMapping("/login")
    public String login(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        // 获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {

            // UsernamePasswordToken() 自动跳转自定义Realm
            subject.login(new UsernamePasswordToken(username,password));
            System.out.println("======登陆成功=========");
            return "======登陆成功=========";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
    return null;
    }
    
    @GetMapping("/logout")
    public String logou() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "退出成功";
    }
}
