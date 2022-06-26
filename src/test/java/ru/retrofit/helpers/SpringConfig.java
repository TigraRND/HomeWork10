package ru.retrofit.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import ru.retrofit.services.AuthorizationService;
import ru.retrofit.services.ResourceService;
import ru.retrofit.services.UserService;

@Configuration
@ComponentScan("ru.retrofit")
public class SpringConfig {
    private static final String REQ_RES_IN_BASE_URI = "https:/reqres.in/api/";
    private static final String CHUCK_NORRIS_BASE_URI = "https://api.chucknorris.io/jokes/";

    private static final Retrofit reqResInRetrofit = RetrofitFactory.buildRetrofit(REQ_RES_IN_BASE_URI);
    private static final Retrofit chuckNorrisRetrofit = RetrofitFactory.buildRetrofit(CHUCK_NORRIS_BASE_URI);

    //TODO сделать что-нибудь с методом getService()

    @Bean
    public UserService userService() {
        return reqResInRetrofit.create(UserService.class);
    }

    @Bean
    public ResourceService resourceService() {
        return reqResInRetrofit.create(ResourceService.class);
    }

    @Bean
    public AuthorizationService authorizationService() {
        return reqResInRetrofit.create(AuthorizationService.class);
    }
}