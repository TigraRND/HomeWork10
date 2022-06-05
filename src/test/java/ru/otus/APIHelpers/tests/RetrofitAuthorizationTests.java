package ru.otus.APIHelpers.tests;

import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.LoginPasswordReq;
import ru.otus.APIHelpers.dto.responses.AuthSuccessResp;
import ru.otus.APIHelpers.dto.responses.ErrorResp;
import ru.otus.APIHelpers.managers.AuthManager;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class RetrofitAuthorizationTests {
    private final AuthManager authManager = new AuthManager();
    private final static String VALID_LOGIN = "eve.holt@reqres.in";
    private final static String PASSWORD = "12345678";

    @Test
    @DisplayName("POST LOGIN - success")
    public void checkUserSuccessAuthorization() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail(VALID_LOGIN);
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userAuthorization(reqBody);
        AuthSuccessResp authSuccessDTO = authManager.getBody(response, AuthSuccessResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(authSuccessDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, response.code()),
                () -> assertNotNull(authSuccessDTO.getToken())
        );
    }

    @Test
    @DisplayName("POST LOGIN - user not found")
    public void checkIncorrectUserOnAuthorization() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail("test@gmail.com");
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userAuthorization(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("user not found", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST LOGIN - miss password")
    public void checkMissPasswordOnAuthorization() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail(VALID_LOGIN);

        Response<ResponseBody> response = authManager.userAuthorization(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing password", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST LOGIN - miss login")
    public void checkMissLoginOnAuthorization() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userAuthorization(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST LOGIN - empty credentials")
    public void checkEmptyCredentialsOnAuthorization() {
        LoginPasswordReq reqBody = new LoginPasswordReq();

        Response<ResponseBody> response = authManager.userAuthorization(reqBody);
        ErrorResp errorDTO = authManager.getBody(response, ErrorResp.class);

        log.info(authManager.dtoToJson(reqBody));
        log.info(authManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }
}
