package com.adminfortrain.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*方法的返回值中，有实体类才能被扫描到，不然扫描不到！！！*/


    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo2())
                .groupName("普光")
                ;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //enable配置是否启动此docket
//                .enable(true)
//                .select()
                //RequestHandlerSelectors 配置要扫描接口的方式
                //basePackage:指定要扫描的包
                //默认全部扫描
                //withClassAnnotation:扫描类上的注解，参数是一个注解的反射对象
                //withMethodAnnotation:扫描方法上的注解
//                .apis(RequestHandlerSelectors)
                //PathSelectors 配置过滤路径的方式
                //ant 指定路径
//                .paths(PathSelectors)
//                .build()
                .groupName("世杰")
                ;
    }

    public ApiInfo apiInfo() {
        return new ApiInfo("世杰的文档",
                "加油努力",
                "1.0",
                "https://github.com/baobei5507/adminForTrain",
                new Contact("世杰", "https://github.com/baobei5507?tab=repositories", "550759734@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    public ApiInfo apiInfo2() {
        return new ApiInfo("普光的文档",
                "加油努力",
                "1.0",
                "https://github.com/baobei5507/adminForTrain",
                new Contact("世杰", "https://github.com/baobei5507?tab=repositories", "550759734@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
