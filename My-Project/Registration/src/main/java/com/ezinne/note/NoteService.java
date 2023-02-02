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

    private final static String REGISTRATION_LINK = "http://localhost:8081/api/v1/registration";

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
            return String.format("user needs to sign up first. Navigate to this url %s", REGISTRATION_LINK);
        }
        noteRepository.save(note);
        return "your note is saved";
    }

    public String editNotes(Long noteId, Note newNote) {
        return noteRepository.findById(noteId)
                .map(oldNote -> {
                    oldNote.setMyNotes(newNote.getMyNotes());
                    noteRepository.save(newNote);
                    return "myNotes has been edited";
                }).orElseThrow(() -> new IllegalArgumentException(String.format("%d does not exist", noteId)));
    }

    public String deleteNote(Long noteId) {
      noteRepository.deleteById(noteId);
      return String.format("Notes with %d has been deleted", noteId);
    }

}
