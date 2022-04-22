package com.vector.springbootshiro.dao;

import com.vector.springbootshiro.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    int save(User user);
    User findAllByuserName(String username);
}
