package com.adminfortrain;


import com.adminfortrain.admin.impl.UserServiceImpl;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class AdminForTrainApplicationTests {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserMapper userMapper;



    @Test
    void testformybatis() {
        List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);
    }

    @Test
    void testformybatisPlus() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
        System.out.println(users);
    }

    @Test
    void test3() {

    }

}
