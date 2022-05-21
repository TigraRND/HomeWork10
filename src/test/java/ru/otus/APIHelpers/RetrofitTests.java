package ru.otus.APIHelpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.apihelpers.ReqResManager;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class RetrofitTests {
    ReqResManager reqResManager = new ReqResManager();

    @Test
    @DisplayName("GET LIST USERS - success")
    void checkUsersList() {
        int pageNum = 1;
        Response<ListUsersResp> resp = reqResManager.getUserList(pageNum);
        ListUsersResp listUsersDTO = resp.body();

        log.info("Количество пользователей на текущей странице: " + listUsersDTO.getData().size());
        log.info("Всего записей: " + listUsersDTO.getTotal());
        log.info("Всего страниц: " + listUsersDTO.getTotalPages());
        log.info(resp.body());

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
        Response<ListUsersResp> resp = reqResManager.getUserList(pageNum);
        ListUsersResp listUsersDTO = resp.body();

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
        Response<SingleUserResp> resp = reqResManager.getUser(userId);
        SingleUserResp singleUserDTO = resp.body();

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
        Response<SingleUserResp> resp = reqResManager.getUser(userId);
        SingleUserResp singleUserDTO = resp.body();

        assertAll(
                () -> assertEquals(HttpStatus.SC_NOT_FOUND, resp.code()),
                () -> assertNull(singleUserDTO)
        );
    }

    @Test
    @DisplayName("GET LIST RESOURCE - success")
    void checkResourceList() {
        int pageNum = 2;
        Response<ListResourceResp> resp = reqResManager.getResourceList(pageNum);
        ListResourceResp listResourceDTO = resp.body();

        log.info("Количество ресурсов на текущей странице: " + listResourceDTO.getData().size());
        log.info("Всего записей: " + listResourceDTO.getTotal());
        log.info("Всего страниц: " + listResourceDTO.getTotalPages());
        log.info(resp.body());

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
                () -> assertEquals(pageNum, listResourceDTO.getPage()),
                () -> assertEquals(listResourceDTO.getPerPage(), listResourceDTO.getData().size()),
                () -> assertNotNull(listResourceDTO.getData().get(0)),
                () -> assertNotNull(listResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET LIST RESOURCE - empty set")
    void checkEmptyResourceList() {
        int pageNum = 5;
        Response<ListResourceResp> resp = reqResManager.getResourceList(pageNum);
        ListResourceResp listResourceDTO = resp.body();

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
                () -> assertEquals(pageNum, listResourceDTO.getPage()),
                () -> assertEquals(0, listResourceDTO.getData().size()),
                () -> assertNotNull(listResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET SINGLE RESOURCE - success")
    void checkGettingSingleResource() {
        int resourceId = 12;
        Response<SingleResourceResp> resp = reqResManager.getResource(resourceId);
        SingleResourceResp singleResourceDTO = resp.body();

        assertAll(
                () -> assertEquals(HttpStatus.SC_OK, resp.code()),
                () -> assertEquals(resourceId, singleResourceDTO.getData().getId()),
                () -> assertEquals("honeysuckle", singleResourceDTO.getData().getName()),
                () -> assertEquals(2011, singleResourceDTO.getData().getYear()),
                () -> assertEquals("#D94F70", singleResourceDTO.getData().getColor()),
                () -> assertEquals("18-2120", singleResourceDTO.getData().getPantoneValue()),
                () -> assertNotNull(singleResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET SINGLE RESOURCE - not found")
    void checkSingleResourceNotFound() {
        int resourceId = 16;
        Response<SingleResourceResp> resp = reqResManager.getResource(resourceId);
        SingleResourceResp singleResourceDTO = resp.body();

        assertAll(
                () -> assertEquals(HttpStatus.SC_NOT_FOUND, resp.code()),
                () -> assertNull(singleResourceDTO)
        );
    }

    @Test
    @DisplayName("Проверка блока Support")
    void checkSupportObject() {
        int pageNum = 1;
        int objectId = 4;
        SupportResp expectedDTO = ReqResManager.jsonFileToDTO("SupportDTO.json", SupportResp.class);

        ListUsersResp listUsersDTO = reqResManager.getUserList(pageNum).body();
        SingleUserResp singleUserDTO = reqResManager.getUser(objectId).body();
        ListResourceResp listResourceDTO = reqResManager.getResourceList(pageNum).body();
        SingleResourceResp singleResourceResp = reqResManager.getResource(objectId).body();

        assertEquals(expectedDTO,listUsersDTO.getSupport());

        assertAll(
                () -> assertEquals(expectedDTO,listUsersDTO.getSupport()),
                () -> assertEquals(expectedDTO,singleUserDTO.getSupport()),
                () -> assertEquals(expectedDTO,listResourceDTO.getSupport()),
                () -> assertEquals(expectedDTO,singleResourceResp.getSupport())
        );
    }

    @Test
    @DisplayName("POST CREATE USER - success")
    void checkUserCreation() {
        String userFirstName = "Ronald";
        String userLastName = "MacDonald";
        String userJob = "Clown";
        String userEmail = "ronald@macdonald.com";

        Response<CreateUserResp> resp = reqResManager
                .createUser(new CreateUserReq(
                        userFirstName,
                        userLastName,
                        userJob,
                        userEmail
                ));
        CreateUserResp createUserDTO = resp.body();

        log.info(createUserDTO);

        assertAll(
                () -> assertEquals(HttpStatus.SC_CREATED, resp.code()),
                () -> assertEquals(userFirstName, createUserDTO.getFirstName()),
                () -> assertEquals(userLastName, createUserDTO.getLastName()),
                () -> assertEquals(userJob, createUserDTO.getJob()),
                () -> assertEquals(userEmail, createUserDTO.getEmail()),
                () -> assertFalse(createUserDTO.getId().isBlank()),
                () -> assertFalse(createUserDTO.getCreatedAt().isBlank())
        );
    }

    @Test
    @DisplayName("DELETE USER - success")
    void checkUserDeleting() {
        int userId = 2;
        Response<Void> resp = reqResManager.deleteUser(userId);
        assertEquals(HttpStatus.SC_NO_CONTENT, resp.code());
    }
}