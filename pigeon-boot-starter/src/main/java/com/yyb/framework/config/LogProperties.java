package com.yyb.framework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description 通过 ConfigurationProperties定义同时该Bean中定义对应的属性
 * 得到配置项
 * 如下  配置request.log.enabled
 */

@Setter
@Getter
@ConfigurationProperties(prefix = "request.log")
public class LogProperties {

    private Boolean enabled = Boolean.FALSE;
}