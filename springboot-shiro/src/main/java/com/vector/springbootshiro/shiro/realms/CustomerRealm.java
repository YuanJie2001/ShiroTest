package com.vector.springbootshiro.shiro.realms;

import com.vector.springbootshiro.entity.Perms;
import com.vector.springbootshiro.entity.User;
import com.vector.springbootshiro.service.UserService;
import com.vector.springbootshiro.shiro.salt.MyByteSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


import java.util.List;


/**
 * @author WangJiaHui
 * @description:
 * @ClassName CustomerRealm
 * @date 2022/4/17 17:19
 */
@Component
// 自定义realm
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    // 获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("调用授权验证:" + primaryPrincipal);
        // 根据主身份信息获取角色信息 和权限信息
        User user = userService.findRoleListByUsername(primaryPrincipal);
        /**
         * 授权角色
         */
        if (!CollectionUtils.isEmpty(user.getRoleList())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoleList().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
                //  获得对应角色的权限信息
                List<Perms> perms = userService.findPermsByRoleId(String.valueOf(role.getId()));
                if (!CollectionUtils.isEmpty(perms)){
                    perms.forEach(perm -> {
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    // 获取认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取登陆用户名身份信息
        String principal = (String) authenticationToken.getPrincipal();
        System.out.println(principal);
        User user = userService.findAllByuserName(principal);
        // 数据库用户名与登陆用户名比较
        if (!ObjectUtils.isEmpty(user)) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
