package com.vector.springbootshiro.dao;

import com.vector.springbootshiro.entity.Perms;
import com.vector.springbootshiro.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface UserDao {
    int save(User user);

    User findAllByuserName(String username);

    /**
     * 根据用户名查询所有角色
     */
    User findRoleListByUsername(String username);

    /**
     * 根据角色id查询权限集合
     */
    List<Perms> findPermsByRoleId(String id);
}
