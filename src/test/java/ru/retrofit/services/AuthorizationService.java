package ru.retrofit.services;

import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.retrofit.dto.requests.LoginPasswordReq;

@Component
public interface AuthorizationService {

    @POST("register")
    Call<ResponseBody> userRegistration(@Body LoginPasswordReq body);

    @POST("login")
    Call<ResponseBody> userAuthorization(@Body LoginPasswordReq body);
}
