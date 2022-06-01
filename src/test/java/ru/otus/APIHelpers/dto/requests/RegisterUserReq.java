package ru.otus.APIHelpers.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserReq {
    private String email;
    private String password;
}
