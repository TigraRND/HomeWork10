package ru.retrofit.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.retrofit.dto.responses.ErrorResp;

import java.io.File;
import java.io.IOException;

@Log4j2
public abstract class RootUtils {
    private final ObjectMapper mapper = new ObjectMapper();

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

    public void logBody(Object obj, String message) {
        String parametrizedMessage = message + ":\n{}";
        try {
            log.info(parametrizedMessage, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        } catch (JsonProcessingException exception) {
            log.error("Ошибка парсинга объекта в json");
        }
    }

    public void logBody(Object obj) {
        logBody(obj, "Тело сообщения");
    }
}