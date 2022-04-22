package com.vector.springbootshiro.service.impl;

import com.vector.springbootshiro.Utils.SaltUtils;
import com.vector.springbootshiro.dao.UserDao;
import com.vector.springbootshiro.entity.User;
import com.vector.springbootshiro.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName UserServiceImpl
 * @date 2022/4/20 15:44
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public boolean save(User user) {
        // 处理业务调用的dao
        // 1. 生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 2. 将随机盐加入数据库
        user.setSalt(salt);
        // 明文密码进行 md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        return userDao.save(user)>0;
    }

    @Override
    public User findAllByuserName(String username) {
        return userDao.findAllByuserName(username);
    }
}
