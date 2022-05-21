package ru.otus.APIHelpers.apihelpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.*;
import ru.otus.APIHelpers.services.ReqResService;

import java.io.File;
import java.io.IOException;

@Log4j2
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

    public static SupportResp jsonFileToDTO(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("src/test/resources/jsonExamples/" + fileName), SupportResp.class);
        } catch (IOException exception) {
            log.error("Не найден файл: {}", fileName);
            return null;
        }
    }

    @SneakyThrows
    public Response<ListUsersResp> getUserList(int pageNum) {
        return service
                .getUserList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<SingleUserResp> getUser(int userId) {
        return service
                .getUser(userId)
                .execute();
    }

    @SneakyThrows
    public Response<ListResourceResp> getResourceList(int pageNum) {
        return service
                .getResourceList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<SingleResourceResp> getResource(int resourceId) {
        return service
                .getResource(resourceId)
                .execute();
    }

    @SneakyThrows
    public Response<CreateUserResp> createUser(CreateUserReq reqBody) {
        return service
                .createUser(reqBody)
                .execute();
    }

    @SneakyThrows
    public Response<Void> deleteUser(int useId) {
        return service
                .deleteUser(useId)
                .execute();
    }

}
