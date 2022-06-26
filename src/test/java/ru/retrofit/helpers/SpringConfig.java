package ru.retrofit.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.retrofit.services.AuthorizationService;
import ru.retrofit.services.ResourceService;
import ru.retrofit.services.UserService;

@Configuration
@ComponentScan("ru.retrofit")
public class SpringConfig {
    //TODO сделать что-нибудь с методом getService()

    @Bean
    public UserService userService() {
        return RetrofitFactory.reqResInSite().create(UserService.class);
    }

    @Bean
    public ResourceService resourceService() {
        return RetrofitFactory.reqResInSite().create(ResourceService.class);
    }

    @Bean
    public AuthorizationService authorizationService() {
        return RetrofitFactory.reqResInSite().create(AuthorizationService.class);
    }
}