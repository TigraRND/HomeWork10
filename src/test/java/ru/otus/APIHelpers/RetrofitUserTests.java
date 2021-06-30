package ru.otus.APIHelpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SpringBootTest
class RetrofitUserTests {
	private Logger log = LogManager.getLogger(RetrofitUserTests.class);
	Response<User> response;
	//Endpoint service for send request
	APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

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
//		TODO передать в метод User DTO
		String body = "{\n" +
				"    \"name\": \"morpheus\",\n" +
				"    \"job\": \"leader\"\n" +
				"}";
		response = service.createUser(body).execute();
		Assertions.assertEquals(201,response.code());
		logging();
	}

	@Test
	@DisplayName("GET - LIST USERS")
	void checkUsersList() throws IOException {
		response = service.listUsers().execute();
		Assertions.assertEquals(201,response.code());
		logging();
	}

	void logging(){
		log.info("\nResponse code: " + response.code() + "\nBody: " + response.body());
	}
}