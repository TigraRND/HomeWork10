package ru.otus.APIHelpers.managers;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.services.AuthorizationService;

public class RegisterManager extends RootManager {

    private final AuthorizationService authorizationService = getService(AuthorizationService.class);

    @SneakyThrows
    public Response<ResponseBody> userRegistration(RegisterUserReq reqBody) {
        return authorizationService.
                userRegistration(reqBody)
                .execute();
    }
}
