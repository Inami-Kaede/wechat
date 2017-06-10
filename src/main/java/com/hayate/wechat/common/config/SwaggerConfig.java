package com.hayate.wechat.common.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
    	
    	//VendorExtension<String> v = new StringVendorExtension("name1", "value1");
    	
    	//没搞懂这是啥
    	Collection<VendorExtension> list = new ArrayList<VendorExtension>();
    	//list.add(v);
    	  	
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(new ApiInfo("微信接口", "按照文档模块分", "0.0.1", "www.baidu.com", new Contact("Hayate", "moe.hao123.com", "hayatenokiseki@gmail.com"), "点击我进入微信文档", "https://mp.weixin.qq.com/wiki",list))
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }
    
}
