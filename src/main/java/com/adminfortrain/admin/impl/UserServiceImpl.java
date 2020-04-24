package com.adminfortrain.admin.impl;

import com.adminfortrain.admin.model.User;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
