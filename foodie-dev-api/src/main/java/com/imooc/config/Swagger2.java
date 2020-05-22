package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @FileName Swagger2
 * @Description
 * @Author jiuhao
 * @Date 2020/5/16 16:37
 * @Modified
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    // /swagger-ui.html
    // /doc.html

    @Bean
    public Docket createRestApi() {
        // 指定api类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())                                    // 定义api文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.imooc.controller")) //指定controller所在包
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口api")
                .contact(new Contact("ChenDehua", "www.rxwycdh.cn", "597701764@qq.com"))
                .description("转为天天吃货提供的api文档")
                .version("1.0.1")
                .termsOfServiceUrl("www.rxwycdh.cn")                   // 网站地址
                .build();
    }
}
