package com.vector.springbootshiro.config;

import com.vector.springbootshiro.shiro.cache.RedisCacheManager;
import com.vector.springbootshiro.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangJiaHui
 * @description:
 * @ClassName ShiroConfig
 * @date 2022/4/17 17:09
 */
@Configuration
public class ShiroConfig {
    /**
     * 1.创建shiroFilter 拦截所有请求
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 配置系统公共资源 web访问路径
        Map<String, String> map = new HashMap<>();
        map.put("/login/**", "anon"); // anon设为公共资源要设在authc上面,开放访问登陆注册页面,.否则请求登陆注册页面资源一直被重定向
        map.put("/register/**", "anon");
        map.put("/user/**", "anon");
        map.put("/index.html", "anon"); // 开放访问未授权要跳转的页面.否则目标路径一直被重定向
        // 配置系统受限资源
        map.put("/**", "authc"); // authc 请求这个资源路径需要被授权,除了登陆页面和主页其余全部需要认证
        /**
         * 访问所有受限资源都会默认返回到login.jsp
         * shiroFilterFactoryBean.setLoginUrl()
         * 或yml shiro.loginUrl="/index.html"  index.html 修改跳转主页
         * 会和 static-path-pattern: /resources/** 冲突
         */
        shiroFilterFactoryBean.setLoginUrl("/index.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 2.创建安全管理器
     *
     * @param realm
     * @return
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 给安全管理器设置
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    /**
     * 3. 创建自定义Realm
     */
    @Bean("realm")
    public Realm getRealm(RedisCacheManager redisCacheManager) {
        CustomerRealm customerRealm = new CustomerRealm();
        // 修改凭证校验匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher =
                new HashedCredentialsMatcher();
        // 设置加密算法为md5
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数
        hashedCredentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        // 开启缓存管理
        customerRealm.setCacheManager(redisCacheManager);
        // 开启全局缓存
        customerRealm.setCachingEnabled(true);
        // 开启认证缓存
        customerRealm.setAuthenticationCachingEnabled(true);
        customerRealm.setAuthenticationCacheName("authenticationCache");
        // 开启授权缓存
        customerRealm.setAuthorizationCachingEnabled(true);
        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }

}
