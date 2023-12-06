package com.electronic.voting.config;

import com.electronic.voting.config.scope.ViewScope;
import java.util.HashMap;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public static ViewScope viewScope() {
        return new ViewScope();
    }

    /**
     * Allows the use of @Scope("view") on Spring @Component, @Service and
     * @Controller beans
     */
    @Bean
    public static CustomScopeConfigurer scopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("view", viewScope());
        configurer.setScopes(hashMap);
        return configurer;
    }

}
