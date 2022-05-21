package ru.otus.APIHelpers.services;

import retrofit2.Call;
import retrofit2.http.*;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.*;

public interface ReqResService {
    @GET("users")
    Call<ListUsersResp> getUserList(@Query("page") int pageNum);

    @GET("users/{id}")
    Call<SingleUserResp> getUser(@Path("id") int id);

    @GET("unknown")
    Call<ListResourceResp> getResourceList(@Query("page") int pageNum);

    @GET("unknown/{id}")
    Call<SingleResourceResp> getResource(@Path("id") int id);

    @POST("users")
    Call<CreateUserResp> createUser(@Body CreateUserReq body);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
