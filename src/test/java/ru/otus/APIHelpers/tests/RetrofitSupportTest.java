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
public class RetrofitSupportTest {
    UserManager userManager = new UserManager();
    ResourceManager resourceManager = new ResourceManager();

    @Test
    @DisplayName("Проверка блока Support")
    void checkSupportObject() {
        int pageNum = 1;
        int objectId = 4;
        SupportResp expectedDTO = UserManager.jsonFileToDTO("SupportDTO.json", SupportResp.class);

        ListUsersResp listUsersDTO = userManager.getUserList(pageNum).body();
        SingleUserResp singleUserDTO = userManager.getUser(objectId).body();
        ListResourceResp listResourceDTO = resourceManager.getResourceList(pageNum).body();
        SingleResourceResp singleResourceResp = resourceManager.getResource(objectId).body();

        log.info("Список пользователей:\n{}", UserManager.dtoToJson(listUsersDTO.getSupport()));
        log.info("Сингл пользователь:\n{}", UserManager.dtoToJson(singleUserDTO.getSupport()));
        log.info("Список ресурсов:\n{}", UserManager.dtoToJson(listResourceDTO.getSupport()));
        log.info("Сингл ресурс:\n{}", UserManager.dtoToJson(singleResourceResp.getSupport()));

        assertAll(
                () -> assertEquals(expectedDTO, listUsersDTO.getSupport()),
                () -> assertEquals(expectedDTO, singleUserDTO.getSupport()),
                () -> assertEquals(expectedDTO, listResourceDTO.getSupport()),
                () -> assertEquals(expectedDTO, singleResourceResp.getSupport())
        );
    }
}
