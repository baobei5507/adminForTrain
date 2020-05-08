package com.adminfortrain.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                ;
    }

    public ApiInfo apiInfo(){
       return new ApiInfo("世杰的文档",
               "加油努力",
               "1.0",
               "https://github.com/baobei5507/adminForTrain",
                new Contact("世杰", "https://github.com/baobei5507?tab=repositories", "550759734@qq.com"),
               "Apache 2.0",
               "http://www.apache.org/licenses/LICENSE-2.0",
               new ArrayList());
    }

}
