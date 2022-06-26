package ru.retrofit.reqres.in.managers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.reqres.in.dto.responses.ListResourceResp;
import ru.retrofit.reqres.in.dto.responses.SingleResourceResp;
import ru.retrofit.reqres.in.services.ResourceService;

@Component
public class ResourceManager {
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
