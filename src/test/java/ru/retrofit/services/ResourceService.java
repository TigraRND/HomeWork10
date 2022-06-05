package ru.retrofit.services;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.retrofit.dto.responses.ListResourceResp;
import ru.retrofit.dto.responses.SingleResourceResp;

@Component
public interface ResourceService {

    @GET("unknown")
    Call<ListResourceResp> getResourceList(@Query("page") int pageNum);

    @GET("unknown/{id}")
    Call<SingleResourceResp> getResource(@Path("id") int id);
}
