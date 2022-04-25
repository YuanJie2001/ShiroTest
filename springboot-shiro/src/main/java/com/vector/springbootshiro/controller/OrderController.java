package com.vector.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName OrderController
 * @date 2022/4/22 16:38
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/save")
//    @RequiresRoles(value = {"superAdmin","admin"}) // shiro判断角色
//    @RequiresPermissions("user:update:*")
    public String save(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("admin")){
            System.out.println("保存订单!");
        } else {
            System.out.println("无权访问!");
        }
        return "order";
    }
}
