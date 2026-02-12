package com.yuansaas.user.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * 统一配置类
 *
 * @author LXZ 2026/2/12 10:29
 */
@Component
@Getter
public class AppProperties {
    
    
    @Value("${app.default-password}")
    private String defaultPassword;

    public String getDefaultPassword() {
        return defaultPassword;
    }
}
