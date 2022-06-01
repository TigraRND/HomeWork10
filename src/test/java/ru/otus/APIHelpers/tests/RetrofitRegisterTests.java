package ru.otus.APIHelpers.tests;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.dto.responses.RegisterSuccessResp;
import ru.otus.APIHelpers.managers.RegisterManager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
public class RetrofitRegisterTests {
    private final RegisterManager registerManager = new RegisterManager();

    @Test
    @DisplayName("POST REGISTER - success")
    public void checkUserSuccessRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail("eve.holt@reqres.in");
        reqBody.setPassword("12345678");

        Response<RegisterSuccessResp> resp = registerManager.userRegistration(reqBody);
        RegisterSuccessResp registerSuccessDTO = resp.body();

        log.info(RegisterManager.dtoToJson(reqBody));
        log.info(RegisterManager.dtoToJson(registerSuccessDTO));

        assertAll(
                () -> assertNotNull(registerSuccessDTO.getId()),
                () -> assertNotNull(registerSuccessDTO.getToken())
        );
    }
}
