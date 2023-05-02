package com.ezinne.registration;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
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

    public String deleteRegisteredUser(Long id){
        return appUserService.deleteUser(id);
    }
}
