package ru.otus.APIHelpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.pojo.requests.CreateUserReq;
import ru.otus.APIHelpers.pojo.responses.CreateUserResp;
import ru.otus.APIHelpers.pojo.ListUsersResp;
import ru.otus.APIHelpers.pojo.responses.SingleUserResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SpringBootTest
class RetrofitTests {
	private Logger log = LogManager.getLogger(RetrofitTests.class);
	Response<SingleUserResp> response;
	//Endpoint service for send request
	ReqResService service = ReqResManager.getClient().create(ReqResService.class);

	@Test
	@DisplayName("GET - SINGLE USER")
	void checkGettingSingleUser() throws IOException {
		response = service.getUserById().execute();

		if(response.isSuccessful()){
			logging();
		}else{
			log.info("Response ERROR");
		}
	}

	@Test
	@DisplayName("GET - SINGLE USER NOT FOUND")
	void checkSingleUserNotFound() throws IOException {
		response = service.userNotFound().execute();
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

	@Test
	@DisplayName("GET - LIST USERS")
	void checkUsersList() throws IOException {
		Response<ListUsersResp> responseListUsers;
		ListUsersResp listUsersResp;

		responseListUsers = service.listUsers(2).execute();
		listUsersResp = responseListUsers.body();

//		log.info("Размер списка data: " + listUsersResponse.getData().size());
		log.info("Всего записей: " + listUsersResp.getTotal());
		log.info("Всего страниц: " + listUsersResp.getTotalPages());

		Assertions.assertEquals(200,responseListUsers.code());
		Assertions.assertEquals(listUsersResp.getPerPage(), listUsersResp.getData().size());
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