package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;


    public String saveNotes(Long userId, Note notes) {

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));


        Boolean loggedIn = appUser.getLoggedIn();
        if (loggedIn) {

        notes.setTimestamp(ZonedDateTime.now());
        notes.setAppUser(appUser);
        noteRepository.save(notes);

        return "Title - " + notes.getNoteTitle() + ": " + notes.getMyNotes() + " created at " + notes.getTimestamp();
    }
        return"user needs to login";

    }
    public String editNotes(Long noteId, Note newNote) {
        Note existingNotes = noteRepository.findById(noteId).
                    orElseThrow(() -> new IllegalArgumentException(String.format("%d does not exist", noteId)));

        existingNotes.setMyNotes(newNote.getMyNotes());
        newNote.setId(existingNotes.getId());
        newNote.setNoteTitle(existingNotes.getNoteTitle());
        newNote.setAppUser(existingNotes.getAppUser());
        newNote.setTimestamp(ZonedDateTime.now());

        noteRepository.save(newNote);
        return newNote.getMyNotes() + ". Last modified at " + newNote.getTimestamp();

    }

    public List<Note> listOfNotesForUser(Long appUserId) {

        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        return appUser.getNotes();

    }

    public String deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
        return String.format("note with %d has been deleted", noteId);
    }

}