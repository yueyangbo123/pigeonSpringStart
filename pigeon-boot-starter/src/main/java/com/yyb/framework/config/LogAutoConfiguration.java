package com.yyb.framework.config;

import com.yyb.framework.interceptors.LogInterceptor;
import com.yyb.framework.task.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description
 */


@Configuration
@EnableConfigurationProperties({LogProperties.class})
@ConditionalOnProperty(prefix = "request.log" , name = {"enabled"}, havingValue = "true"
)
public class LogAutoConfiguration {
    //prefix为配置文件中的前缀,
    //name为配置的名字
    //havingValue是与配置的值对比值,当两个值相同返回true,配置类生效.
    public void addInfo() {
        System.out.println("LogAutoConfiguration生效了");
    }


    @Bean
    public Product getProduct() {
        return new Product();
    }


}