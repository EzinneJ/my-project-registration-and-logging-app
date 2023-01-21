package com.ezinne.login;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public String login(LoginRequest request) {

        return appUserService.loginUser(new AppUser(
                request.getEmail(),
                request.getPassword()
        ));
    }

    public Optional<AppUser> findByEmail(String email) {
       return appUserRepository.findByEmail(email);
    }
}
