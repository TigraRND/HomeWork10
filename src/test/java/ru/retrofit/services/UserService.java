package ru.retrofit.services;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.*;
import ru.retrofit.dto.requests.CreateUserReq;
import ru.retrofit.dto.responses.CreateUserResp;
import ru.retrofit.dto.responses.ListUsersResp;
import ru.retrofit.dto.responses.SingleUserResp;
import ru.retrofit.dto.responses.UpdateUserResp;

@Component
public interface UserService {
    @GET("users")
    Call<ListUsersResp> getUserList(@Query("page") int pageNum);

    @GET("users")
    Call<ListUsersResp> getUserList(@Query("page") int pageNum,
                                    @Query("delay") int delay);

    @GET("users/{id}")
    Call<SingleUserResp> getUser(@Path("id") int id);

    @POST("users")
    Call<CreateUserResp> createUser(@Body CreateUserReq body);

    @PUT("users/{id}")
    Call<UpdateUserResp> updateUserPut(@Path("id") int userId, @Body CreateUserReq body);

    @PATCH("users/{id}")
    Call<UpdateUserResp> updateUserPatch(@Path("id") int userId, @Body CreateUserReq body);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
