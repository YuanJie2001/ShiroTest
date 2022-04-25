package com.vector.springbootshiro.service;
import com.vector.springbootshiro.entity.Perms;
import com.vector.springbootshiro.entity.User;

import java.util.List;

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

    User findRoleListByUsername(String username);

    // 根据角色id查询权限集合
    List<Perms> findPermsByRoleId(String id);
}
