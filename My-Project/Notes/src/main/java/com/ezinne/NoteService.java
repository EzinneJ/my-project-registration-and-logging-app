package com.ezinne;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class NoteService {
    private NoteRepository noteRepository;
    private RestTemplate restTemplate;

    private final static String REGISTRATION_LINK = "/api/v1/registration";
    
    public String saveNotes(Note notes) {
        Note note = new Note();
        note.setNotes(notes.getNotes());


        AppUser appUser = restTemplate.getForObject("/api/v1/login/" + email, AppUser.class);



        assert appUser != null;
        if (appUser.getEmail().isEmpty()) {
            return String.format("user needs to sign up first. Navigate to this url %S", REGISTRATION_LINK);
        }
        noteRepository.save(note);
        return "your note is saved";
    }

}
