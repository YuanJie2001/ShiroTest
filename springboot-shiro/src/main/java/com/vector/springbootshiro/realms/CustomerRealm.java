package com.vector.springbootshiro.realms;

import com.mysql.cj.util.StringUtils;
import com.vector.springbootshiro.entity.User;
import com.vector.springbootshiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;


/**
 * @author WangJiaHui
 * @description:
 * @ClassName CustomerRealm
 * @date 2022/4/17 17:19
 */
@Component
// 自定义realm
public class CustomerRealm extends AuthorizingRealm {
    @Resource(name = "userService")
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取登陆用户名身份信息
        String principal = (String) authenticationToken.getPrincipal();
        System.out.println(principal);
        User user = userService.findAllByuserName(principal);
        // 模拟数据库用户名与登陆用户名比较
        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        }
        return null;
    }
}
