package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;
    @Mock
    private AppUserRepository appUserRepository;
    @InjectMocks
    private NoteService noteService;

    private Note note;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
       appUser  = new AppUser(1L);
       appUserRepository.save(appUser);
    note = new Note();
    note.setId(1L);
    note.setNoteTitle("test title");
    note.setMyNotes("test note");
    note.setTimestamp(ZonedDateTime.now());
    note.setAppUser(appUser);
    appUser.setLoggedIn(true);
    noteRepository.save(note);

    }

    @Test
    void saveNoteSuccess() {

        when(appUserRepository.findById(anyLong())).thenReturn(Optional.ofNullable(appUser));

        String result = noteService.saveNotes(appUser.getId(), note);

        assertEquals("Title - " + note.getNoteTitle() + ": " + note.getMyNotes() + " with id: " + note.getId() + " created at " + note.getTimestamp(), result);
        verify(appUserRepository).save(any());
    }

    @Test
    void userNotFound() throws Exception {

        assertThatThrownBy(() -> noteService.saveNotes(appUser.getId(), note))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("user not found");
    }

    @Test
    void editNoteSuccess() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));
        String result = noteService.editNotes(note.getId(), note);
        assertEquals(note.getMyNotes() + ". Last modified at " + note.getTimestamp(), result);
    }
    @Test
    void editNoteUnsuccessful() {
        assertThatThrownBy(() -> noteService.editNotes(note.getId(), note))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 does not exist");
    }

    @Test
    void deleteNote() {

        String result = noteService.deleteNoteById(note.getId());

        assertEquals("note with 1 has been deleted", result);
    }

}

