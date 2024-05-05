package com.erica.gamsung.global.config;

import com.erica.gamsung.global.config.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SecurityConfig.class};
    }

}