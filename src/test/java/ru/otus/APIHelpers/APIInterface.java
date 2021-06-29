package ru.otus.APIHelpers;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.otus.APIHelpers.pojo.User;

public interface APIInterface {
    @GET("2")
    Call <User> getUserById();

    @GET("23")
    Call <User> getUserNotFound();
}
