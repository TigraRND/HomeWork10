package ru.otus.APIHelpers;

import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.APIHelpers.pojo.requests.CreateUserReq;
import ru.otus.APIHelpers.pojo.responses.CreateUserResp;
import ru.otus.APIHelpers.pojo.ListUsersResp;
import ru.otus.APIHelpers.pojo.responses.SingleUserResp;

public interface ReqResService {
    @GET("users/2")
    Call <SingleUserResp> getUserById();

    @GET("users/23")
    Call <SingleUserResp> userNotFound();

    @DELETE("users/2")
    Call<SingleUserResp> deleteUser();

    @POST("users")
    Call<CreateUserResp> createUser(@Body CreateUserReq body);

    @GET("users")
    Call <ListUsersResp> listUsers(@Query("page") int pageNum);
}
