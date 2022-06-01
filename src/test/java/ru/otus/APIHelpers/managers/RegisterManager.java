package ru.otus.APIHelpers.managers;

import lombok.SneakyThrows;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.dto.responses.RegisterErrorResp;
import ru.otus.APIHelpers.dto.responses.RegisterSuccessResp;
import ru.otus.APIHelpers.services.AuthorizationService;

public class RegisterManager extends RootManager {

    private final AuthorizationService authorizationService = getService(AuthorizationService.class);

    @SneakyThrows
    public Response<RegisterSuccessResp> userRegistration(RegisterUserReq reqBody) {
        return authorizationService.
                userRegistration(reqBody)
                .execute();
    }
}
