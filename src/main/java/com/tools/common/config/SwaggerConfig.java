package com.tools.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * API 配置
 * @author 小柒2012
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket appApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统应用API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tools.module.app"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket sysApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统配置API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tools.module.sys"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("炒鸡工具箱")
                .description("一个能够让程序猿快速开发的炒鸡工具箱")
                .termsOfServiceUrl("http://blog.52itstyle.vip")
                .contact(new Contact("爪哇笔记 ", "http://blog.52itstyle.vip", "345849402@qq.com"))
                .version("1.0").build();
    }
}
