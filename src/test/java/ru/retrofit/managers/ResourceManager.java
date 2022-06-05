package ru.retrofit.managers;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.dto.responses.ListResourceResp;
import ru.retrofit.dto.responses.SingleResourceResp;
import ru.retrofit.services.ResourceService;

@Component
public class ResourceManager extends RootManager {
    private final ResourceService resourceService = getService(ResourceService.class);

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
