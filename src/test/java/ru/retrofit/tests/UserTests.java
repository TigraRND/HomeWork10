package ru.retrofit.tests;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.retrofit.dto.requests.CreateUserReq;
import ru.retrofit.dto.responses.CreateUserResp;
import ru.retrofit.dto.responses.ListUsersResp;
import ru.retrofit.dto.responses.SingleUserResp;
import ru.retrofit.dto.responses.UpdateUserResp;
import ru.retrofit.managers.UserManager;

import static java.net.HttpURLConnection.*;
import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class UserTests {
    @Autowired
    private UserManager userManager;

    private static final CreateUserReq TEST_USER = new CreateUserReq(
            "Ronald",
            "MacDonald",
            "Clown",
            "ronald@macdonald.com"
    );

    @Test
    @DisplayName("GET LIST USERS - success")
    void checkUsersList() {
        int pageNum = 1;
        Response<ListUsersResp> resp = userManager.getUserList(pageNum);
        ListUsersResp listUsersDTO = resp.body();

        log.info(userManager.dtoToJson(listUsersDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(pageNum, listUsersDTO.getPage()),
                () -> assertEquals(listUsersDTO.getPerPage(), listUsersDTO.getData().size()),
                () -> assertNotNull(listUsersDTO.getData().get(0)),
                () -> assertNotNull(listUsersDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET LIST USERS - empty set")
    void checkEmptyUserList() {
        int pageNum = 4;
        Response<ListUsersResp> resp = userManager.getUserList(pageNum);
        ListUsersResp listUsersDTO = resp.body();

        log.info(userManager.dtoToJson(listUsersDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(pageNum, listUsersDTO.getPage()),
                () -> assertEquals(0, listUsersDTO.getData().size()),
                () -> assertNotNull(listUsersDTO.getSupport())
        );
    }


    @Test
    @DisplayName("GET SINGLE USER - success")
    void checkGettingSingleUser() {
        int userId = 10;
        Response<SingleUserResp> resp = userManager.getUser(userId);
        SingleUserResp singleUserDTO = resp.body();

        log.info(userManager.dtoToJson(singleUserDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(userId, singleUserDTO.getData().getId()),
                () -> assertEquals("byron.fields@reqres.in", singleUserDTO.getData().getEmail()),
                () -> assertEquals("Byron", singleUserDTO.getData().getFirstName()),
                () -> assertEquals("Fields", singleUserDTO.getData().getLastName()),
                () -> assertEquals("https://reqres.in/img/faces/" + userId + "-image.jpg", singleUserDTO.getData().getAvatar()),
                () -> assertNotNull(singleUserDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET SINGLE USER - not found")
    void checkSingleUserNotFound() {
        int userId = 14;
        Response<SingleUserResp> resp = userManager.getUser(userId);
        SingleUserResp singleUserDTO = resp.body();

        log.info(userManager.dtoToJson(singleUserDTO));

        assertAll(
                () -> assertEquals(HTTP_NOT_FOUND, resp.code()),
                () -> assertNull(singleUserDTO)
        );
    }

    @Test
    @DisplayName("POST CREATE USER - success")
    void checkUserCreation() {
        Response<CreateUserResp> resp = userManager.createUser(TEST_USER);
        CreateUserResp createUserRespDTO = resp.body();

        log.info("Создаем пользователя:\n{}", userManager.dtoToJson(TEST_USER));
        log.info("Создан пользователь:\n{}", userManager.dtoToJson(createUserRespDTO));

        assertAll(
                () -> assertEquals(HTTP_CREATED, resp.code()),
                () -> assertEquals(TEST_USER.getFirstName(), createUserRespDTO.getFirstName()),
                () -> assertEquals(TEST_USER.getLastName(), createUserRespDTO.getLastName()),
                () -> assertEquals(TEST_USER.getJob(), createUserRespDTO.getJob()),
                () -> assertEquals(TEST_USER.getEmail(), createUserRespDTO.getEmail()),
                () -> assertFalse(createUserRespDTO.getId().isBlank()),
                () -> assertFalse(createUserRespDTO.getCreatedAt().isBlank())
        );
    }

    @Test
    @DisplayName("PUT UPDATE USER - success")
    void checkUpdateUserByPut() {
        Response<UpdateUserResp> resp = userManager.updateUserPut(3, TEST_USER);
        UpdateUserResp updateUserRespDTO = resp.body();

        log.info("Создаем пользователя:\n{}", userManager.dtoToJson(TEST_USER));
        log.info("Создан пользователь:\n{}", userManager.dtoToJson(updateUserRespDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(TEST_USER.getFirstName(), updateUserRespDTO.getFirstName()),
                () -> assertEquals(TEST_USER.getLastName(), updateUserRespDTO.getLastName()),
                () -> assertEquals(TEST_USER.getJob(), updateUserRespDTO.getJob()),
                () -> assertEquals(TEST_USER.getEmail(), updateUserRespDTO.getEmail()),
                () -> assertFalse(updateUserRespDTO.getUpdatedAt().isBlank())
        );
    }

    @Test
    @DisplayName("PATCH UPDATE USER - success")
    void checkUpdateUserByPatch() {
        Response<UpdateUserResp> resp = userManager.updateUserPatch(3, TEST_USER);
        UpdateUserResp updateUserRespDTO = resp.body();

        log.info("Создаем пользователя:\n{}", userManager.dtoToJson(TEST_USER));
        log.info("Создан пользователь:\n{}", userManager.dtoToJson(updateUserRespDTO));

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(TEST_USER.getFirstName(), updateUserRespDTO.getFirstName()),
                () -> assertEquals(TEST_USER.getLastName(), updateUserRespDTO.getLastName()),
                () -> assertEquals(TEST_USER.getJob(), updateUserRespDTO.getJob()),
                () -> assertEquals(TEST_USER.getEmail(), updateUserRespDTO.getEmail()),
                () -> assertFalse(updateUserRespDTO.getUpdatedAt().isBlank())
        );
    }

    @Test
    @DisplayName("DELETE USER - success")
    void checkUserDeleting() {
        int userId = 2;
        Response<Void> resp = userManager.deleteUser(userId);
        assertEquals(HTTP_NO_CONTENT, resp.code());
    }

    @Test
    @DisplayName("GET LIST USERS - delayed")
    void checkDelayedResponse() {
        int pageNum = 2;
        int delay = 3;
        Response<ListUsersResp> resp = userManager.getUserList(pageNum, delay);
        ListUsersResp listUsersDTO = resp.body();

        log.info(userManager.dtoToJson(listUsersDTO));

        assertAll(
                () -> assertTrue(resp.isSuccessful()),
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(pageNum, listUsersDTO.getPage()),
                () -> assertEquals(listUsersDTO.getPerPage(), listUsersDTO.getData().size()),
                () -> assertNotNull(listUsersDTO.getData().get(0)),
                () -> assertNotNull(listUsersDTO.getSupport())
        );
    }
}