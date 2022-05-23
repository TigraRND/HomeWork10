package ru.otus.APIHelpers.managers;

import lombok.SneakyThrows;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.responses.ListResourceResp;
import ru.otus.APIHelpers.dto.responses.SingleResourceResp;
import ru.otus.APIHelpers.services.ResourceService;

public class ResourceManager extends RootManager {
    private final ResourceService resourceService;

    public ResourceManager() {
        resourceService = retrofit.create(ResourceService.class);
    }

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
