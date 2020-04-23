package com.adminfortrain;

import com.adminfortrain.mapper.UserMapper;
import com.adminfortrain.model.User;
import com.adminfortrain.service.UserService;
import com.adminfortrain.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
class AdminForTrainApplicationTests {

    @Autowired
    UserServiceImpl userService;


    @Test
    void testformybatis() {
        System.out.println(userService.queryUserByName("keke"));
    }



}
