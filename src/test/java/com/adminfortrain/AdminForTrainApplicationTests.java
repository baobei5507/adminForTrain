package com.adminfortrain;


import com.adminfortrain.admin.impl.UserServiceImpl;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.adminfortrain.vipAccount.impl.VipServiceImpl;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class AdminForTrainApplicationTests {

    @Autowired
    VipServiceImpl vipService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    VipMapper vipMapper;

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
        List<Vip> vips = vipMapper.selectList(null);
        vips.forEach(System.out::println);
    }


    @Test
    void test5(){
        Page<Vip> page = new Page<>(1,5);
       vipMapper.selectPage(page, null);
        List<Vip> vips = page.getRecords();
        for (Vip vip : vips) {
            if(vip.getEndtime().compareTo(new Date()) == -1)
                vipMapper.deleteById(vip.getId());
        }
        System.out.println(new Date());
    }

    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username","aaa");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user.getUsername());
    }

    @Test
    void test7(){
        List<Vip> del = vipMapper.getDel(1, 5);
        del.forEach(System.out::println);
    }

    @Test
    void test8(){
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",10,20);
        List<Object> list = vipMapper.selectObjs(queryWrapper);
        list.forEach(System.out::println);
    }
}
