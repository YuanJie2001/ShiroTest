package com.vector.springbootshiro.controller;

import com.vector.springbootshiro.Utils.Result;
import com.vector.springbootshiro.Utils.VerifyCodeUtils;
import com.vector.springbootshiro.entity.User;
import com.vector.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    /**
     * 验证码校验
     *
     * @param
     * @return
     */
    @GetMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code", code);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, outputStream, code);

    }

    // 用户注册
    @PostMapping("/register")
    public Object register(User user) {

        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>().error(500, "服务器内部错误");
        }
        return new Result<>().success(200, "注册成功");

    }


    @PostMapping("/login")
    public Object login(User user, String code, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 比较验证码
        String codes = (String) session.getAttribute("code");
        if (codes.equalsIgnoreCase(code)) {
            // 获取主体对象
            Subject subject = SecurityUtils.getSubject();
            try {

                // UsernamePasswordToken() 自动跳转自定义Realm
                subject.login(new UsernamePasswordToken(username, password));
                return "======登陆成功=========";
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误");
            }
        }
        return new Result<>().error(500, "验证码错误");
    }

    @GetMapping("/logout")
    public String logou() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "退出成功";
    }
}
