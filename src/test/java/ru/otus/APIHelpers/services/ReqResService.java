package ru.otus.APIHelpers.services;

import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.CreateUserResp;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;

public interface ReqResService {
    @GET("users")
    Call <ListUsersResp> getUserList(@Query("page") int pageNum);

    @GET("users/{id}")
    Call <SingleUserResp> getUserById(@Path("id") int id);

    @DELETE("users/2")
    Call<SingleUserResp> deleteUser();

    @POST("users")
    Call<CreateUserResp> createUser(@Body CreateUserReq body);
}
