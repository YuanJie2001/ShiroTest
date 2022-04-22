package com.vector.springbootshiro.service;

import com.vector.springbootshiro.entity.User;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName UserService
 * @date 2022/4/20 15:44
 */

public interface UserService {
    // 注册方法
    boolean save(User user);
    // 根据用户名查询业务的方法
    User findAllByuserName(String username);
}
