package ru.otus.APIHelpers.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class RestAssuredTests {

	@Test
	void restAssuredTest() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("https://reqres.in/api/users/2")
				.then()
				.extract().response();

		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
		Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
	}
}