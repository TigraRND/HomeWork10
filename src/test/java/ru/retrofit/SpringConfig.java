package ru.retrofit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.retrofit.managers.RootManager;
import ru.retrofit.services.AuthorizationService;
import ru.retrofit.services.ResourceService;
import ru.retrofit.services.UserService;

@Configuration
@ComponentScan("ru.retrofit")
public class SpringConfig extends RootManager {

    @Bean
    public UserService userService() {
        return getService(UserService.class);
    }

    @Bean
    public ResourceService resourceService() {
        return getService(ResourceService.class);
    }

    @Bean
    public AuthorizationService authorizationService() {
        return getService(AuthorizationService.class);
    }
}
