package ru.otus.APIHelpers.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.dto.responses.RegisterSuccessResp;
import ru.otus.APIHelpers.managers.RegisterManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
public class RetrofitRegisterTests {
    private final RegisterManager registerManager = new RegisterManager();

    @Test
    @DisplayName("POST REGISTER - success")
    public void checkUserSuccessRegistration() throws IOException {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail("eve.holt@reqres.in");
        reqBody.setPassword("12345678");

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        ObjectMapper mapper = new ObjectMapper();
        RegisterSuccessResp registerSuccessDTO = mapper.readValue(response.body().string(), RegisterSuccessResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(registerSuccessDTO));

        assertAll(
                () -> assertNotNull(registerSuccessDTO.getId()),
                () -> assertNotNull(registerSuccessDTO.getToken())
        );
    }

    @Test
    @DisplayName("POST REGISTER - incorrrect user")
    public void checkIncorrectUserOnRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail("test@gmail.com");
        reqBody.setPassword("12345678");


    }
}
