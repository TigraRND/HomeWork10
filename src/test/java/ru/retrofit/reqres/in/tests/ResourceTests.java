package ru.retrofit.reqres.in.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.retrofit.reqres.in.dto.responses.ListResourceResp;
import ru.retrofit.reqres.in.dto.responses.SingleResourceResp;
import ru.retrofit.helpers.RootUtils;
import ru.retrofit.reqres.in.managers.ResourceManager;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceTests extends RootUtils {
    @Autowired
    private ResourceManager resourceManager;

    @Test
    @DisplayName("GET LIST RESOURCE - success")
    void checkResourceList() {
        int pageNum = 2;
        Response<ListResourceResp> resp = resourceManager.getResourceList(pageNum);
        ListResourceResp listResourceDTO = resp.body();

        logBody(listResourceDTO);

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(pageNum, listResourceDTO.getPage()),
                () -> assertEquals(listResourceDTO.getPerPage(), listResourceDTO.getData().size()),
                () -> assertNotNull(listResourceDTO.getData().get(0)),
                () -> assertNotNull(listResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET LIST RESOURCE - empty set")
    void checkEmptyResourceList() {
        int pageNum = 5;
        Response<ListResourceResp> resp = resourceManager.getResourceList(pageNum);
        ListResourceResp listResourceDTO = resp.body();

        logBody(listResourceDTO);

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(pageNum, listResourceDTO.getPage()),
                () -> assertEquals(0, listResourceDTO.getData().size()),
                () -> assertNotNull(listResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET SINGLE RESOURCE - success")
    void checkGettingSingleResource() {
        int resourceId = 12;
        Response<SingleResourceResp> resp = resourceManager.getResource(resourceId);
        SingleResourceResp singleResourceDTO = resp.body();

        logBody(singleResourceDTO);

        assertAll(
                () -> assertEquals(HTTP_OK, resp.code()),
                () -> assertEquals(resourceId, singleResourceDTO.getData().getId()),
                () -> assertEquals("honeysuckle", singleResourceDTO.getData().getName()),
                () -> assertEquals(2011, singleResourceDTO.getData().getYear()),
                () -> assertEquals("#D94F70", singleResourceDTO.getData().getColor()),
                () -> assertEquals("18-2120", singleResourceDTO.getData().getPantoneValue()),
                () -> assertNotNull(singleResourceDTO.getSupport())
        );
    }

    @Test
    @DisplayName("GET SINGLE RESOURCE - not found")
    void checkSingleResourceNotFound() {
        int resourceId = 16;
        Response<SingleResourceResp> resp = resourceManager.getResource(resourceId);
        SingleResourceResp singleResourceDTO = resp.body();

        logBody(singleResourceDTO);

        assertAll(
                () -> assertEquals(HTTP_NOT_FOUND, resp.code()),
                () -> assertNull(singleResourceDTO)
        );
    }
}
