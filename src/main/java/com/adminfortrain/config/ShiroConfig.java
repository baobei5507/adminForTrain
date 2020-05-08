package com.adminfortrain.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //shiroFilterFactoryBean
    @Bean                                                      //此处Qualifier绑定的是getDefaultSecurityManager这个bean对象
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        //前两步固定套路，工厂模式创建一个shiroFilterFactoryBean,然后将下面的defaultWebSecurityManager设置进来
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);

        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        //注意此处的页面路劲不是网页名称，而是MVC中的方法
//        filterMap.put("/main","anon");
//        bean.setFilterChainDefinitionMap(filterMap);

        //设置账户权限不足时返回路径
//        bean.setLoginUrl("/index");
        return bean;
    }

    //DefaultWebSecurityManager,中间商，处理用户 授权 以及 认证 的合法性，以此转交给上面的FilterFactorBean进行过滤
    @Bean(name = "securityManager")                             //此处Qualifier绑定的是userRealm这个bean对象
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //关联Realm
        defaultWebSecurityManager.setRealm(userRealm);

        return defaultWebSecurityManager;
    }

    //创建Realm对象,用来进行授权以及认证功能,完毕后转交给SecurityManager进行合法性验证
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}

