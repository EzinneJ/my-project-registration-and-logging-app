package com.ezinne.appUser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public String signUpUser(AppUser appUser) {
        String email = appUser.getEmail();

        Optional<AppUser> userExists = appUserRepository.findByEmail(email);

        if (userExists.isPresent()) {
            return String.format("user with %s already exists", email);
        }

        setBCryptPasswordEncoder(appUser);

        appUserRepository.save(appUser);
        return "user has signed up";
    }

    public String loginUser(AppUser appUser) {
        String email = appUser.getEmail();
        Optional<AppUser> emailExists = appUserRepository.findByEmail(email);

        if (emailExists.isEmpty() && !email.matches(appUserRepository.findByEmail(email).toString())) {
            return "user needs to sign up";
        }
            return "User is successfully logged in, user can now save notes";
        }

        private void setBCryptPasswordEncoder (AppUser appUser){
            String encodePassword = bCryptPasswordEncoder.encode("password");
            appUser.setPassword(encodePassword);
        }

    }
