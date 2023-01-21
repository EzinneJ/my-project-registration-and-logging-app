package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import com.ezinne.appUser.AppUserService;
import com.ezinne.login.LoginRequest;
import com.ezinne.login.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@AllArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;

    private final AppUserRepository appUserRepository;

    private final AppUserService appUserService;
    private final static String REGISTRATION_LINK = "http://localhost:8081/api/v1/registration";
    private final static String LOGIN_LINK = "http://localhost:8081/api/v1/login";

    public String saveNotes(Note notes) {
        Note note = new Note();
        note.setMyNotes(notes.getMyNotes());
        AppUser appUser = AppUser.builder()
                .email(notes.getAppUser().getEmail())
                .password(notes.getAppUser().getPassword())
                .build();
        note.setAppUser(appUser);

        String email = note.getAppUser().getEmail();
        Optional<AppUser> appUserExists = appUserRepository.findByEmail(email);

        if (appUserExists.isEmpty()) {
            return String.format("user needs to sign up first. Navigate to this url %S", REGISTRATION_LINK);
        }
        noteRepository.save(note);
        return "your note is saved";
    }

}
