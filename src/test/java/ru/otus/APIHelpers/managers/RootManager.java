package ru.otus.APIHelpers.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.otus.APIHelpers.dto.responses.ErrorResp;

import java.io.File;
import java.io.IOException;

@Log4j2
public abstract class RootManager {
    protected static final String BASE_URL = "https:/reqres.in/api/";
    protected Retrofit retrofit;

    private final ObjectMapper mapper = new ObjectMapper();

    public RootManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    protected <T> T getService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    @SneakyThrows
    public <T> T getBody(Response<ResponseBody> response, Class<T> clazz) {
        if (clazz.isAssignableFrom(ErrorResp.class)) {
            return mapper.readValue(response.errorBody().string(), clazz);
        } else {
            return mapper.readValue(response.body().string(), clazz);
        }
    }

    public <T> T jsonFileToDTO(String fileName, Class<T> clazz) {
        try {
            return mapper.readValue(new File("src/test/resources/jsonExamples/" + fileName), clazz);
        } catch (IOException exception) {
            log.error("Не найден файл: {}", fileName);
            return null;
        }
    }

    public String dtoToJson(Object obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            log.error("Ошибка парсинга объекта в json");
            return null;
        }
    }
}