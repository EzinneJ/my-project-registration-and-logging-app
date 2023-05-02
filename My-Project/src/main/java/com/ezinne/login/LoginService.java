package com.ezinne.login;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;

    public String login(LoginRequest request) {

        return appUserService.loginUser(new AppUser(request.getEmail(), request.getPassword()));
    }
}
