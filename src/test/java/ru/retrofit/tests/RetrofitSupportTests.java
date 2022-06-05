package ru.retrofit.tests;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.retrofit.dto.responses.*;
import ru.retrofit.managers.ResourceManager;
import ru.retrofit.managers.UserManager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
@SpringBootTest
class RetrofitSupportTests {
    @Autowired
    private UserManager userManager;

    @Autowired
    private ResourceManager resourceManager;

    @Test
    @DisplayName("Проверка блока Support")
    void checkSupportObject() {
        int pageNum = 1;
        int objectId = 4;
        SupportResp expectedDTO = userManager.jsonFileToDTO("SupportDTO.json", SupportResp.class);

        ListUsersResp listUsersDTO = userManager.getUserList(pageNum).body();
        SingleUserResp singleUserDTO = userManager.getUser(objectId).body();
        ListResourceResp listResourceDTO = resourceManager.getResourceList(pageNum).body();
        SingleResourceResp singleResourceResp = resourceManager.getResource(objectId).body();

        log.info("Список пользователей:\n{}", userManager.dtoToJson(listUsersDTO.getSupport()));
        log.info("Сингл пользователь:\n{}", userManager.dtoToJson(singleUserDTO.getSupport()));
        log.info("Список ресурсов:\n{}", userManager.dtoToJson(listResourceDTO.getSupport()));
        log.info("Сингл ресурс:\n{}", userManager.dtoToJson(singleResourceResp.getSupport()));

        assertAll(
                () -> assertEquals(expectedDTO, listUsersDTO.getSupport()),
                () -> assertEquals(expectedDTO, singleUserDTO.getSupport()),
                () -> assertEquals(expectedDTO, listResourceDTO.getSupport()),
                () -> assertEquals(expectedDTO, singleResourceResp.getSupport())
        );
    }
}
