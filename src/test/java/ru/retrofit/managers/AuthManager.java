package ru.retrofit.managers;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.dto.requests.LoginPasswordReq;
import ru.retrofit.services.AuthorizationService;

@Component
public class AuthManager extends RootManager {

    private final AuthorizationService authorizationService = getService(AuthorizationService.class);

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