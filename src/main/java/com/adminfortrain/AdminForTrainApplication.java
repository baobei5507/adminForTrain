package com.adminfortrain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.adminfortrain.admin.mapper","com.adminfortrain.vipAccount.mapper"})
public class AdminForTrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminForTrainApplication.class, args);
    }

}
