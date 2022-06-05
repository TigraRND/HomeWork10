package ru.retrofit.managers;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.dto.requests.LoginPasswordReq;
import ru.retrofit.services.AuthorizationService;

@Component
public class AuthManager extends RootManager {

    @Autowired
    private AuthorizationService authorizationService;

    @SneakyThrows
    public Response<ResponseBody> userRegistration(LoginPasswordReq reqBody) {
        return authorizationService.
                userRegistration(reqBody)
                .execute();
    }

    @SneakyThrows
    public Response<ResponseBody> userAuthorization(LoginPasswordReq reqBody) {
        return authorizationService.
                userAuthorization(reqBody)
                .execute();
    }
}