package ru.otus.APIHelpers.tests;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.dto.responses.ErrorResp;
import ru.otus.APIHelpers.dto.responses.RegisterSuccessResp;
import ru.otus.APIHelpers.managers.RegisterManager;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
public class RetrofitRegisterTests {
    private final RegisterManager registerManager = new RegisterManager();
    private final static String VALID_LOGIN = "eve.holt@reqres.in";
    private final static String PASSWORD = "12345678";

    @Test
    @DisplayName("POST REGISTER - success")
    public void checkUserSuccessRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail(VALID_LOGIN);
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        RegisterSuccessResp registerSuccessDTO = registerManager.getBody(response, RegisterSuccessResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(registerSuccessDTO));

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.code()),
                () -> assertNotNull(registerSuccessDTO.getId()),
                () -> assertNotNull(registerSuccessDTO.getToken())
        );
    }

    @Test
    @DisplayName("POST REGISTER - incorrect user")
    @SneakyThrows
    public void checkIncorrectUserOnRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail("test@gmail.com");
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        ErrorResp errorDTO = registerManager.getBody(response, ErrorResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HttpStatus.SC_BAD_REQUEST, response.code()),
                () -> assertEquals("Note: Only defined users succeed registration", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - miss password")
    @SneakyThrows
    public void checkMissPasswordOnRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setEmail(VALID_LOGIN);

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        ErrorResp errorDTO = registerManager.getBody(response, ErrorResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HttpStatus.SC_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing password", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - miss login")
    @SneakyThrows
    public void checkMissLoginOnRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();
        reqBody.setPassword(PASSWORD);

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        ErrorResp errorDTO = registerManager.getBody(response, ErrorResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HttpStatus.SC_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }

    @Test
    @DisplayName("POST REGISTER - empty credentials")
    @SneakyThrows
    public void checkEmptyCredentialsOnRegistration() {
        RegisterUserReq reqBody = new RegisterUserReq();

        Response<ResponseBody> response = registerManager.userRegistration(reqBody);
        ErrorResp errorDTO = registerManager.getBody(response, ErrorResp.class);

        log.info(registerManager.dtoToJson(reqBody));
        log.info(registerManager.dtoToJson(errorDTO));

        assertAll(
                () -> assertEquals(HttpStatus.SC_BAD_REQUEST, response.code()),
                () -> assertEquals("Missing email or username", errorDTO.getError())
        );
    }
}
