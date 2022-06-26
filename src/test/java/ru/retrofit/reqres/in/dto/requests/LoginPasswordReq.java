package ru.retrofit.reqres.in.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPasswordReq {
    private String email;
    private String password;
}
