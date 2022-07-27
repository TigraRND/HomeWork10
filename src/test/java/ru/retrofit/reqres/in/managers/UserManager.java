package ru.retrofit.reqres.in.managers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import ru.retrofit.reqres.in.dto.requests.CreateUserReq;
import ru.retrofit.reqres.in.dto.responses.CreateUserResp;
import ru.retrofit.reqres.in.dto.responses.ListUsersResp;
import ru.retrofit.reqres.in.dto.responses.SingleUserResp;
import ru.retrofit.reqres.in.dto.responses.UpdateUserResp;
import ru.retrofit.reqres.in.services.UserService;

import java.util.Map;

@Component
public class UserManager {
    @Autowired
    private UserService userService;

    @SneakyThrows
    public Response<ListUsersResp> getUserList(int pageNum) {
        return userService
                .getUserList(pageNum)
                .execute();
    }

    @SneakyThrows
    public Response<ListUsersResp> getUserList(Map<String, Integer> queryMap) {
        return userService
                .getUserList(queryMap)
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