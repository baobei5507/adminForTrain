package com.adminfortrain.admin.impl;

import com.adminfortrain.admin.model.User;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杰
 * @since 2020-04-24
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insertUser(User user){
        userMapper.insert(user);
    }
}
