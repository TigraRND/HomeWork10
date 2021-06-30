package ru.otus.APIHelpers;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.otus.APIHelpers.pojo.User;

public interface APIInterface {
    @GET("users/2")
    Call <User> getUserById();

    @GET("users/23")
    Call <User> userNotFound();

    @DELETE("users/2")
    Call<User> deleteUser();

    @POST("users/")
    Call<User> createUser(String body);

    @GET("users?page=2")
    Call <User> listUsers();
}
