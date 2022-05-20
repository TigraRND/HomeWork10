package ru.otus.APIHelpers;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.apihelpers.ReqResManager;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.CreateUserResp;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class RetrofitTests {
	ReqResManager reqResManager = new ReqResManager();
	Response<SingleUserResp> response;

	@Order(1)
	@Test
	@DisplayName("GET LIST USERS - success")
	void checkUsersList() {
		int pageNum = 1;
		Response<ListUsersResp> resp = reqResManager.getUserList(pageNum);
		ListUsersResp listUsersDTO = resp.body();

		log.info("Количество пользователей на текущей странице: " + listUsersDTO.getData().size());
		log.info("Всего записей: " + listUsersDTO.getTotal());
		log.info("Всего страниц: " + listUsersDTO.getTotalPages());

		assertAll(
				() -> assertEquals(HttpStatus.SC_OK, resp.code()),
				() -> assertEquals(pageNum, listUsersDTO.getPage()),
				() -> assertEquals(listUsersDTO.getPerPage(), listUsersDTO.getData().size()),
				() -> assertNotNull(listUsersDTO.getData())
		);
	}

	@Order(2)
	@Test
	@DisplayName("GET LIST USERS - empty set")
	@SneakyThrows
	void checkEmptyUserList() {
		int pageNum = 4;
		Response<ListUsersResp> resp = reqResManager.getUserList(pageNum);
		ListUsersResp listUsersDTO = resp.body();

		assertAll(
				() -> assertEquals(HttpStatus.SC_OK, resp.code()),
				() -> assertEquals(pageNum, listUsersDTO.getPage()),
				() -> assertEquals(0, listUsersDTO.getData().size())
		);
	}


	@Order(3)
	@Test
	@DisplayName("GET SINGLE USER - success")
	void checkGettingSingleUser() {
		int userId = 10;
		Response<SingleUserResp> resp = reqResManager.getUserById(userId);
		SingleUserResp singleUserDTO = resp.body();

		assertAll(
				() -> assertEquals(HttpStatus.SC_OK, resp.code()),
				() -> assertEquals(userId, singleUserDTO.getData().getId()),
				() -> assertEquals("byron.fields@reqres.in", singleUserDTO.getData().getEmail()),
				() -> assertEquals("Byron", singleUserDTO.getData().getFirstName()),
				() -> assertEquals("Fields", singleUserDTO.getData().getLastName()),
				() -> assertEquals("https://reqres.in/img/faces/" + userId + "-image.jpg", singleUserDTO.getData().getAvatar())
		);
	}

	@Order(4)
	@Test
	@DisplayName("GET SINGLE USER - not found")
	void checkSingleUserNotFound() {
		int userId = 14;
		Response<SingleUserResp> resp = reqResManager.getUserById(userId);
		SingleUserResp singleUserDTO = resp.body();
		assertAll(
				() -> assertEquals(HttpStatus.SC_NOT_FOUND, resp.code()),
				() -> assertNull(singleUserDTO)
		);
	}

	@Test
	@Disabled
	@DisplayName("DELETE - DELETE USER")
	void checkUserDeleting() throws IOException {
		response = reqResManager.service.deleteUser().execute();
		Assertions.assertEquals(204, response.code());
	}

	@Test
	@Disabled
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		Response<CreateUserResp> responseCreateUser;
		CreateUserResp userResponse;

		responseCreateUser = reqResManager.service.createUser(getRequestBody()).execute();

		userResponse = responseCreateUser.body();
		log.info("ID созданного пользователя: " + userResponse.getId());
		log.info("Время создания пользователя: " + userResponse.getCreatedAt());
		log.info("Body ответа от API : " + getRequestBody());

		if(responseCreateUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			log.info("Response ERROR");
		}
		System.out.println(responseCreateUser.body());
		Assertions.assertEquals(201, responseCreateUser.code());
	}

	void logging(){
		log.info("\nResponse code: " + response.code() + "\nBody: " + response.body());
	}

	public CreateUserReq getRequestBody() {
		CreateUserReq requestNewUserData = new CreateUserReq(
				"Morpheus",
				"Leader"
		);
		return requestNewUserData;
	}
}