package ru.retrofit.managers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.dto.responses.ListResourceResp;
import ru.retrofit.dto.responses.SingleResourceResp;
import ru.retrofit.helpers.RootUtils;
import ru.retrofit.services.ResourceService;

@Component
public class ResourceManager extends RootUtils {
    @Autowired
    private ResourceService resourceService;

    @SneakyThrows
    public Response<ListResourceResp> getResourceList(int pageNum) {
        return resourceService
                .getResourceList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<SingleResourceResp> getResource(int resourceId) {
        return resourceService
                .getResource(resourceId)
                .execute();
    }
}
