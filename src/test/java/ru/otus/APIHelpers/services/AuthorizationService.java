package ru.otus.APIHelpers.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.otus.APIHelpers.dto.requests.RegisterUserReq;
import ru.otus.APIHelpers.dto.responses.RegisterSuccessResp;

public interface AuthorizationService {

    @POST("register")
    Call<ResponseBody> userRegistration(@Body RegisterUserReq body);
}
