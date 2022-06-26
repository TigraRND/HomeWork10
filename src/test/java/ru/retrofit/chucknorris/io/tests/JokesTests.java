package ru.retrofit.chucknorris.io.tests;

import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.retrofit.chucknorris.io.dto.responses.SearchResultResp;
import ru.retrofit.chucknorris.io.dto.responses.SingleJokeResp;
import ru.retrofit.chucknorris.io.managers.JokesManager;
import ru.retrofit.helpers.RootUtils;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JokesTests extends RootUtils {
	@Autowired
	private JokesManager jokesManager;

	@Test
	@DisplayName("Тест на рандомную шутку")
	void checkRandomJoke() {
		Response<SingleJokeResp> resp = jokesManager.getRandomJoke();
		SingleJokeResp actualDTO = resp.body();

		logBody(actualDTO);

		assertAll(
				() -> assertEquals(HTTP_OK, resp.code(), "response code not OK"),
				() -> assertNotNull(actualDTO.getCreatedAt(), "created_at is null"),
				() -> assertNotNull(actualDTO.getCreatedAt(), "created_at is null"),
				() -> assertNotNull(actualDTO.getIconUrl(), "icon_url is null"),
				() -> assertNotNull(actualDTO.getId(), "id is null"),
				() -> assertNotNull(actualDTO.getUpdatedAt(), "updated_ad is null"),
				() -> assertNotNull(actualDTO.getUrl(), "url is null"),
				() -> assertNotNull(actualDTO.getValue(), "value is null")
		);
	}

	@Test
	@DisplayName("Тест на поиск по ключевому слову")
	void checkSearchByKeyword() {
		String keyword = "sex";
		Response<ResponseBody> resp = jokesManager.searchByKeyword(keyword);
		SearchResultResp actualDTO = getBody(resp, SearchResultResp.class);

		logBody(actualDTO);

		assertAll(
				() -> assertEquals(HTTP_OK, resp.code()),
				() -> assertTrue(actualDTO.getTotal() > 0),
				() -> assertTrue(actualDTO.getResult().get(0).getValue().contains(keyword))
				//FIXME использовать Stream для проверки всех элементов списка на наличие ключевого слова
		);
	}

	@Test
	@DisplayName("Тест на получение списка категорий")
	void checkCategoriesList() {
		Response<List<String>> resp = jokesManager.getCategories();
		List<String> categories = resp.body();

		logBody(categories, "Список категорий");

		assertAll(
				() -> assertEquals(HTTP_OK, resp.code()),
				() -> assertNotNull(categories),
				() -> assertTrue(categories.size() > 0)
		);
	}

	@Test
	@DisplayName("Тест на поиск по категории")
	void checkSearchByCategory() {
		String category = "movie";
		Response<ResponseBody> resp = jokesManager.getRandomJokeByCategory(category);
		SingleJokeResp actualDTO = getBody(resp, SingleJokeResp.class);

		logBody(actualDTO);

		assertAll(
				() -> assertEquals(HTTP_OK, resp.code()),
				() -> assertEquals(category, actualDTO.getCategories().get(0))
		);
	}

	@Test
	@DisplayName("Тест на поиск по рандомной категории")
	void checkSearchByRandomCategory() {

	}
}
