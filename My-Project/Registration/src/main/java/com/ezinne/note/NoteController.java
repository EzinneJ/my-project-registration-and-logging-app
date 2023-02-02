package com.ezinne.note;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public String saveNote(@RequestBody Note note) {
        return noteService.saveNotes(note);
    }

    @PutMapping("/noteId")
    public String editNote(@PathVariable Long noteId, @RequestBody Note note) {
        return noteService.editNotes(noteId, note);
    }

    @DeleteMapping("/noteId")
    public String deleteNote(@PathVariable Long noteId){
        return noteService.deleteNote(noteId);
    }
}
