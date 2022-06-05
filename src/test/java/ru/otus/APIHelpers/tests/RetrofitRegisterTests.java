package ru.otus.APIHelpers.tests;

import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.LoginPasswordReq;
import ru.otus.APIHelpers.dto.responses.AuthSuccessResp;
import ru.otus.APIHelpers.dto.responses.ErrorResp;
import ru.otus.APIHelpers.managers.AuthManager;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
public class RetrofitRegisterTests {
    private final AuthManager authManager = new AuthManager();
    private final static String VALID_LOGIN = "eve.holt@reqres.in";
    private final static String PASSWORD = "12345678";

    @Test
    @DisplayName("POST REGISTER - success")
    public void checkUserSuccessRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail(VALID_LOGIN);
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        AuthSuccessResp registerSuccessDTO = authManager.getBody(response, AuthSuccessResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(registerSuccessDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, response.code()),
                () -> assertNotNull(registerSuccessDTO.getId()),
                () -> assertNotNull(registerSuccessDTO.getToken())
        );
    }

    @Test
    @DisplayName("POST REGISTER - incorrect user")
    public void checkIncorrectUserOnRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail("test@gmail.com");
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Note: Only defined users succeed registration", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - miss password")
    public void checkMissPasswordOnRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail(VALID_LOGIN);

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing password", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - miss login")
    public void checkMissLoginOnRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - empty credentials")
    public void checkEmptyCredentialsOnRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }
}
