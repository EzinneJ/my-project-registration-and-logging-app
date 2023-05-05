package com.ezinne.note;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> saveNotes(@PathVariable("userId") Long userId, @RequestBody Note note) {
        String result = noteService.saveNotes(userId, note);
        String createdResponse = "Title - " + note.getNoteTitle() + ": " + note.getMyNotes() + " with id: " + note.getId() + " created at " + note.getTimestamp();
        String notFound = String.format("%d does not exist", userId);

        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);


        } else if (result.contains(createdResponse)){
           return ResponseEntity.status(HttpStatus.CREATED).body(createdResponse);
        }
        return  ResponseEntity.badRequest().body("user needs to login");
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<String> editNote(@PathVariable("noteId") Long noteId, @RequestBody Note note) {
        String result = noteService.editNotes(noteId, note);

        String notFound = String.format("%d does not exist", noteId);
        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(note.getMyNotes() + ". Last modified at " + note.getTimestamp());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> notesAssociatedWithUser(@PathVariable("userId") Long userId) {
        String result = noteService.listOfNotesForUser(userId);
        String notFound = String.format("%d does not exist", userId);
        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
       return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{userId}/{start}/{end}")
    public ResponseEntity<String> notesByPeriod(@PathVariable("userId") Long userId,
                                                @PathVariable("start") LocalDate start,
                                                @PathVariable("end") LocalDate end) {

        String result = noteService.notesByPeriodForUser(userId, start, end);

        String notFound = "user not found";
        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
        else {
            String noNotesForPeriod = "No notes was created within this period";
            if (result.contains(noNotesForPeriod)) {
                return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(noNotesForPeriod);
            }
            else {
                String noNoteFoundForUser = "No notes was found for this user";
                if (result.contains(noNoteFoundForUser)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noNoteFoundForUser);
                }
                else {
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }
            }

        }
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNoteById(@PathVariable("noteId") Long noteId) {
        String result = noteService.deleteNoteById(noteId);

        String notFound = String.format("%d does not exist", noteId);
        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("note with %d has been deleted", noteId));
    }
}
