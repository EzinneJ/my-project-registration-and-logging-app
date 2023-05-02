package com.ezinne.note;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveNotes(@PathVariable("userId") Long userId, @RequestBody Note note) {
        return noteService.saveNotes(userId, note);
    }

    @PutMapping("/{noteId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String editNote(@PathVariable("noteId") Long noteId, @RequestBody Note note) {
        return noteService.editNotes(noteId, note);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> notesAssociatedWithUser(@PathVariable("userId") Long userId) {
        List<Note> noteList = noteService.listOfNotesForUser(userId);
        return noteList;
    }

    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String deleteNoteById(@PathVariable("noteId") Long noteId) {
       return noteService.deleteNoteById(noteId);
    }
}
