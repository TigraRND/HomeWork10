package ru.retrofit.chucknorris.io.managers;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.chucknorris.io.dto.responses.SingleJokeResp;
import ru.retrofit.chucknorris.io.services.JokesService;

import java.util.List;

@Component
public class JokesManager {
    @Autowired
    private JokesService jokesService;

    @SneakyThrows
    public Response<SingleJokeResp> getRandomJoke() {
        return jokesService
                .getRandomJoke()
                .execute();
    }

    @SneakyThrows
    public Response<ResponseBody> getRandomJokeByCategory(String category) {
        return jokesService
                .getRandomJokeByCategory(category)
                .execute();
    }

    @SneakyThrows
    public Response<ResponseBody> searchByKeyword(String keyword) {
        return jokesService
                .searchByKeyword(keyword)
                .execute();
    }

    @SneakyThrows
    public Response<List<String>> getCategories() {
        return jokesService
                .getCategories()
                .execute();
    }
}
