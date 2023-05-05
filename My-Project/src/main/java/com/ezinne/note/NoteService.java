package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import com.ezinne.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;


    public String saveNotes(Long userId, Note notes) {

        Optional<AppUser> optionalAppUser = appUserRepository.findById(userId);
        if (optionalAppUser.isEmpty()) {
            return "user not found";
        }
        AppUser appUser = optionalAppUser.get();
        Boolean loggedIn = appUser.getLoggedIn();
        if (loggedIn) {

        notes.setTimestamp(ZonedDateTime.now());
        notes.setAppUser(appUser);
        noteRepository.save(notes);

        return "Title - " + notes.getNoteTitle() + ": " + notes.getMyNotes() + " with id: " + notes.getId() + " created at " + notes.getTimestamp();
    }
        return "user needs to login";

    }
    public String editNotes(Long noteId, Note newNote) {
        Optional<Note> optionalNote = noteRepository.findById(noteId);
        if (optionalNote.isEmpty()){
            return String.format("%d does not exist", noteId);
        }
        Note note = optionalNote.get();

        note.setMyNotes(newNote.getMyNotes());
        newNote.setId(note.getId());
        newNote.setNoteTitle(note.getNoteTitle());
        newNote.setAppUser(note.getAppUser());
        newNote.setTimestamp(ZonedDateTime.now());

        noteRepository.save(newNote);
        return newNote.getMyNotes() + ". Last modified at " + newNote.getTimestamp();

    }

    public String listOfNotesForUser(Long appUserId) {

        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if (optionalAppUser.isEmpty()) {
            return "user not found";
        }
        AppUser appUser = optionalAppUser.get();
        List<Note> notes = appUser.getNotes();
        StringBuilder sb = new StringBuilder();
        for (Note note : notes) {
            sb.append(note.getId());
            sb.append(" - ");
            sb.append(note.getNoteTitle());
            sb.append(": ");
            sb.append(note.getMyNotes());
            sb.append("\n");
        }
       return sb.toString();

    }

    public String notesByPeriodForUser(Long appUserId, LocalDate start, LocalDate end) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if (optionalAppUser.isEmpty()) {
            return "user not found";
        }

        AppUser appUser = optionalAppUser.get();
        List<Note> notes = appUser.getNotes();

        StringBuilder sb = new StringBuilder();
        if (notes.isEmpty()) {
            return "No notes was found for this user";
        }
        for (Note note : notes) {
            ZonedDateTime timestamp = note.getTimestamp();
            LocalDate date = timestamp.toLocalDate();
            if (date.isEqual(start) || date.isEqual(end) || (date.isAfter(start) && date.isBefore(end))) {
                sb.append(note.getId());
                sb.append(" - ");
                sb.append(note.getNoteTitle());
                sb.append(": ");
                sb.append(note.getMyNotes());
                sb.append(": ");
                sb.append(note.getTimestamp());
                sb.append("\n");
                continue;
            }
            return "No notes was created within this period";
        }

        return sb.toString();

    }

    public String deleteNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isEmpty()) {
            return String.format("note with %d does not exist", noteId);
        }
        noteRepository.deleteById(noteId);
        return String.format("note with %d has been deleted", noteId);
    }

}