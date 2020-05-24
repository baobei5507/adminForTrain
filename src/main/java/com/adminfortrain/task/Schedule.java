package com.adminfortrain.task;

import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Lazy
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Resource
    private RedisTemplate<String,Integer> template;

    @Autowired
    private VipMapper vipMapper;


    private  String rediskey;
    private List<Object> vip;
    private  int start;
    private  int end;




    public void  timeup(){
        System.out.println("未走缓存,查询数据库存入缓存中...");
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",this.start,this.end);
        this.vip = this.vipMapper.selectObjs(queryWrapper);
        template.opsForValue().set(this.rediskey,vip.size());
    }

    //定时更新缓存
    @Scheduled(cron = "0 0 0 * * ? ")
    public void  upten(){
        System.out.println("1更新缓存中.....");
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",10,20);
        this.vip = this.vipMapper.selectObjs(queryWrapper);
        template.opsForValue().set("ten",vip.size());
    }

    //定时更新缓存
    @Scheduled(cron = "0 0 0 * * ? ")
    public void  uptwo(){
        System.out.println("2更新缓存中.....");
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",20,30);
        this.vip = this.vipMapper.selectObjs(queryWrapper);
        template.opsForValue().set("two",vip.size());
    }

    //定时更新缓存
    @Scheduled(cron = "0 0 0 * * ? ")
    public void  upthree(){
        System.out.println("3更新缓存中.....");
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",30,40);
        this.vip = this.vipMapper.selectObjs(queryWrapper);
        template.opsForValue().set("three",vip.size());
    }


}
