package ru.otus.APIHelpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.pojo.User;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@SpringBootTest
class ApiHelpersApplicationTests {

	@Test
	@DisplayName("GET SINGLE USER")
	void retrofitTest01() throws IOException {
		Response<User> response;

		//Endpoint service for send request
		APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

		//GET request for get request
		response = service.getUserById().execute();

		if(response.isSuccessful()){
			System.out.println("Response SUCCESS");
			System.out.println(response.body());
		}else{
			System.out.println("Response ERROR");
		}
	}

	@Test
	@DisplayName("GET SINGLE USER NOT FOUND")
	void retrofitTest02() throws IOException {
		Response<User> response;

		//Endpoint service for send request
		APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

		//GET request for get request
		response = service.getUserNotFound().execute();

		Assertions.assertEquals(404,response.code());
	}
}