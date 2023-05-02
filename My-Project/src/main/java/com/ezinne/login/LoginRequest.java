package com.ezinne.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    private final String email;
    private final String password;


}
