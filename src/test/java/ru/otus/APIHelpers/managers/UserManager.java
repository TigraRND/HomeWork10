package ru.otus.APIHelpers.managers;

import lombok.SneakyThrows;
import retrofit2.Response;
import ru.otus.APIHelpers.dto.requests.CreateUserReq;
import ru.otus.APIHelpers.dto.responses.CreateUserResp;
import ru.otus.APIHelpers.dto.responses.ListUsersResp;
import ru.otus.APIHelpers.dto.responses.SingleUserResp;
import ru.otus.APIHelpers.dto.responses.UpdateUserResp;
import ru.otus.APIHelpers.services.UserService;

public class UserManager extends RootManager {
    private final UserService userService = getService(UserService.class);

    @SneakyThrows
    public Response<ListUsersResp> getUserList(int pageNum) {
        return userService
                .getUserList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<SingleUserResp> getUser(int userId) {
        return userService
                .getUser(userId)
                .execute();
    }

    @SneakyThrows
    public Response<CreateUserResp> createUser(CreateUserReq reqBody) {
        return userService
                .createUser(reqBody)
                .execute();
    }

    @SneakyThrows
    public Response<UpdateUserResp> updateUserPut(int userId, CreateUserReq reqBody) {
        return userService
                .updateUserPut(userId, reqBody)
                .execute();
    }

    @SneakyThrows
    public Response<UpdateUserResp> updateUserPatch(int userId, CreateUserReq reqBody) {
        return userService
                .updateUserPatch(userId, reqBody)
                .execute();
    }

    @SneakyThrows
    public Response<Void> deleteUser(int useId) {
        return userService
                .deleteUser(useId)
                .execute();
    }
}