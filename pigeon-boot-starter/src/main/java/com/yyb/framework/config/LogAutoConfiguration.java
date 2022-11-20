package com.yyb.framework.config;

import com.yyb.framework.interceptors.LogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    public void addInfo() {
        System.out.println("LogAutoConfiguration生效了");

    }
}