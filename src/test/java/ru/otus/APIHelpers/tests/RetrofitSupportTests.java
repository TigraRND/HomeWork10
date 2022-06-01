package ru.otus.APIHelpers.tests;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.APIHelpers.dto.responses.*;
import ru.otus.APIHelpers.managers.ResourceManager;
import ru.otus.APIHelpers.managers.UserManager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
class RetrofitSupportTests {
    private final UserManager userManager = new UserManager();
    ResourceManager resourceManager = new ResourceManager();

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
