package ru.retrofit.chucknorris.io.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.retrofit.chucknorris.io.dto.responses.SingleJokeResp;

import java.util.List;

public interface JokesService {
    @GET("random")
    Call<SingleJokeResp> getRandomJoke();

    @GET("random")
    Call<ResponseBody> getRandomJokeByCategory(@Query("category") String category);

    @GET("search")
    Call<ResponseBody> searchByKeyword(@Query("query") String keyword);

    @GET("categories")
    Call<List<String>> getCategories();
}
