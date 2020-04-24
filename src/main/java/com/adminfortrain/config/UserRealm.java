package com.adminfortrain.config;


import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {


    @Autowired
    UserMapper userMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

        //获取当前用户token
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //mybatis-plus查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userToken.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        //后台测试打印用户是否正确获取
        System.out.println(user.getUsername()+" "+user.getPassword());

        //如果用户不存在
        if(user.getUsername() == null)
            return null; //自动抛出UnknownAccountException

        System.out.println(userToken.getUsername()+"......");

        //password siro处理 不交由用户处理
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }
}
