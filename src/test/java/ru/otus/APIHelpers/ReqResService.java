package ru.otus.APIHelpers;

import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.APIHelpers.pojo.CreateUserRequest;
import ru.otus.APIHelpers.pojo.CreateUserResponse;
import ru.otus.APIHelpers.pojo.ListUsersResponse;
import ru.otus.APIHelpers.pojo.User;

public interface ReqResService {
    @GET("users/2")
    Call <User> getUserById();

    @GET("users/23")
    Call <User> userNotFound();

    @DELETE("users/2")
    Call<User> deleteUser();

    @POST("users")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest body);

    @GET("users")
    Call <ListUsersResponse> listUsers(@Query("page") int pageNum);
}
