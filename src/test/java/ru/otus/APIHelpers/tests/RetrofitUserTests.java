package ru.otus.APIHelpers.tests;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.CreateUserResp;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;
import ru.otus.APIHelpers.dto.responses.UpdateUserResp;
import ru.otus.APIHelpers.managers.UserManager;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class RetrofitUserTests {
    private final UserManager userManager = new UserManager();

    private final CreateUserReq TEST_USER = new CreateUserReq(
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_NOT_FOUND, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_CREATED, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
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
        assertEquals(HttpStatus.SC_NO_CONTENT, resp.code());
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
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
                () -> assertEquals(pageNum, listUsersDTO.getPage()),
                () -> assertEquals(listUsersDTO.getPerPage(), listUsersDTO.getData().size()),
                () -> assertNotNull(listUsersDTO.getData().get(0)),
                () -> assertNotNull(listUsersDTO.getSupport())
        );
    }
}