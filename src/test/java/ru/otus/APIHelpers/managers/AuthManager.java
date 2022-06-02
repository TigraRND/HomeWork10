package ru.otus.APIHelpers.managers;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.LoginPasswordReq;
import ru.otus.APIHelpers.services.AuthorizationService;

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