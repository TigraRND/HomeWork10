package ru.otus.APIHelpers;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.managers.ReqResManager;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.CreateUserResp;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;
import ru.otus.APIHelpers.services.ReqResService;

import java.io.IOException;

@Log4j2
@SpringBootTest
class RetrofitTests {
	Response<SingleUserResp> response;
	//Endpoint service for send request
	ReqResService service = ReqResManager.getClient().create(ReqResService.class);

	@Order(1)
	@Test
	@DisplayName("GET LIST USERS - success")
	@SneakyThrows
	void checkUsersList() {
		int pageNum = 1;
		Response<ListUsersResp> listUsersRespBody = service.listUsers(pageNum).execute();
		ListUsersResp listUsersDTO = listUsersRespBody.body();

		log.info("Количество пользователей на текущей странице: " + listUsersDTO.getData().size());
		log.info("Всего записей: " + listUsersDTO.getTotal());
		log.info("Всего страниц: " + listUsersDTO.getTotalPages());

		assertAll(
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
		ListUsersResp listUsersDTO = service
				.listUsers(pageNum)
				.execute()
				.body();

		assertAll(
				() -> assertEquals(pageNum, listUsersDTO.getPage()),
				() -> assertEquals(0, listUsersDTO.getData().size())
		);
	}


	@Order(3)
	@Test
	@Disabled("Переписать после рефакторинга классов сервиса и менеджера")
	@DisplayName("GET SINGLE USER - success")
	@SneakyThrows
	void checkGettingSingleUser() {
		SingleUserResp singleUserDTO = service.getUserById(10).execute().body();

		if(response.isSuccessful()){
			logging();
		}else{
			log.info("Response ERROR");
		}
	}

	@Test
	@DisplayName("GET - SINGLE USER NOT FOUND")
	void checkSingleUserNotFound() throws IOException {
		response = service.getUserById(15).execute();
		Assertions.assertEquals(404,response.code());
		logging();
	}

	@Test
	@DisplayName("DELETE - DELETE USER")
	void checkUserDeleting() throws IOException {
		response = service.deleteUser().execute();
		Assertions.assertEquals(204,response.code());
		logging();
	}

	@Test
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		Response<CreateUserResp> responseCreateUser;
		CreateUserResp userResponse;

		responseCreateUser = service.createUser(getRequestBody()).execute();

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