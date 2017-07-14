package com.hayate.wechat.common.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "com.hayate.wechat" })
public class SwaggerConfig {

    @Bean
    public Docket api() {    	   	  	
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(new ApiInfo("微信接口", "按照文档模块分", "0.0.1", "", new Contact("Hayate", "", "hayatenokiseki@gmail.com"), "点击我进入微信文档", "https://mp.weixin.qq.com/wiki",new ArrayList<VendorExtension>()))
                .select()  // 选择那些路径和api会生成document
                	.apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                	.paths(PathSelectors.any()) // 对所有路径进行监控
                	.build()
                .securitySchemes(newArrayList(apiKey()))
                ;
    }
    
    private ApiKey apiKey() {
  	  return new ApiKey("权限验证", "auth_code", "header");	//16	Here we use ApiKey as the security schema that is identified by the name mykey
  	}
}
