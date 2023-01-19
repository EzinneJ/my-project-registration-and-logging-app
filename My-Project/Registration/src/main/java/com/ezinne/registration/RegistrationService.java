package com.ezinne.registration;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    public String register(RegistrationRequest request) {

        return appUserService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        ));
    }

}
