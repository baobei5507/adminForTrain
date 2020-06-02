package com.adminfortrain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan({"com.adminfortrain.admin.mapper","com.adminfortrain.vipAccount.mapper","com.adminfortrain.Coa.mapper"})
public class AdminForTrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminForTrainApplication.class, args);
    }

}
