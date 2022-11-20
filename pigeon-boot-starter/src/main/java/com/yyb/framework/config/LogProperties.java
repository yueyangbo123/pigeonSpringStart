package com.yyb.framework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description
 */

@Setter
@Getter
@ConfigurationProperties(prefix = "request.log")
public class LogProperties {

    private Boolean enabled = Boolean.FALSE;
}