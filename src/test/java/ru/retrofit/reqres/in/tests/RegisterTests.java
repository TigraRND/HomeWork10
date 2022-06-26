package ru.retrofit.reqres.in.tests;

import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.retrofit.reqres.in.dto.requests.LoginPasswordReq;
import ru.retrofit.reqres.in.dto.responses.AuthSuccessResp;
import ru.retrofit.reqres.in.dto.responses.ErrorResp;
import ru.retrofit.helpers.RootUtils;
import ru.retrofit.reqres.in.managers.AuthManager;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegisterTests extends RootUtils {
    @Autowired
    private AuthManager authManager;
    private final static String VALID_LOGIN = "eve.holt@reqres.in";
    private final static String PASSWORD = "12345678";

    @Test
    @DisplayName("POST REGISTER - success")
    public void checkUserSuccessRegistration() {
        LoginPasswordReq reqBody = new LoginPasswordReq();
        reqBody.setEmail(VALID_LOGIN);
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = authManager.userRegistration(reqBody);
        AuthSuccessResp registerSuccessDTO = getBody(response, AuthSuccessResp.class);

        logBody(reqBody);
        logBody(registerSuccessDTO);

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
        ErrorResp errorDTO = getBody(response, ErrorResp.class);

        logBody(reqBody);
        logBody(errorDTO);

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
        ErrorResp errorDTO = getBody(response, ErrorResp.class);

        logBody(reqBody);
        logBody(errorDTO);

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
        ErrorResp errorDTO = getBody(response, ErrorResp.class);

        logBody(reqBody);
        logBody(errorDTO);

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
        ErrorResp errorDTO = getBody(response, ErrorResp.class);

        logBody(reqBody);
        logBody(errorDTO);

        assertAll(
                () -> assertEquals(HTTP_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }
}