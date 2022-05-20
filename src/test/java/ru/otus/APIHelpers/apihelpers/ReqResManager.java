package ru.otus.APIHelpers.apihelpers;

import lombok.SneakyThrows;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;
import ru.otus.APIHelpers.services.ReqResService;

public class ReqResManager {
    private static final String BASE_URL = "https:/reqres.in/api/";
    public final ReqResService service;

    public ReqResManager() {
        service = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ReqResService.class);
    }

    @SneakyThrows
    public ListUsersResp getUserList(int pageNum) {
        return service
                .getUserList(pageNum)
                .execute()
                .body();
    }

    @SneakyThrows
    public SingleUserResp getUserById(int userId) {
        return service
                .getUserById(userId)
                .execute()
                .body();
    }

}
