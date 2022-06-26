package ru.retrofit.managers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.dto.requests.CreateUserReq;
import ru.retrofit.dto.responses.CreateUserResp;
import ru.retrofit.dto.responses.ListUsersResp;
import ru.retrofit.dto.responses.SingleUserResp;
import ru.retrofit.dto.responses.UpdateUserResp;
import ru.retrofit.helpers.RootUtils;
import ru.retrofit.services.UserService;

@Component
public class UserManager extends RootUtils {
    @Autowired
    private UserService userService;

    @SneakyThrows
    public Response<ListUsersResp> getUserList(int pageNum) {
        return userService
                .getUserList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<ListUsersResp> getUserList(int pageNum, int delay) {
        return userService
                .getUserList(pageNum, delay)
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