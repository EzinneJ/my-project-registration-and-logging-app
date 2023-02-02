package com.ezinne.appUser;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private static final String NOTES_LINK = "http://localhost:8081/api/v1/note";
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
        return String.format("user has signed up. Navigate to %s to start saving your notes", NOTES_LINK);
    }

    public String loginUser(AppUser appUser) {
        String email = appUser.getEmail();
        Optional<AppUser> emailExists = appUserRepository.findByEmail(email);

        if (emailExists.isEmpty() && !email.matches(appUserRepository.findByEmail(email).toString())) {
            return "user needs to sign up";
        }
            return "User is successfully logged in, user can now save notes";
        }

        public String deleteUser(String email) {
            Optional<AppUser> existingEmail = appUserRepository.findByEmail(email);
            appUserRepository.delete(existingEmail.get());
            return String.format("AppUser with %s has been deleted", email);
        }

        private void setBCryptPasswordEncoder (AppUser appUser){
            String encodePassword = bCryptPasswordEncoder.encode("password");
            appUser.setPassword(encodePassword);
        }

    }
