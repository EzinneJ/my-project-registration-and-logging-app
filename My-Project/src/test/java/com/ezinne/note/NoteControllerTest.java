package com.ezinne.note;

import com.ezinne.RegistrationApplication;
import com.ezinne.appUser.AppUser;
import com.ezinne.appUser.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = RegistrationApplication.class)
@Transactional
@Disabled
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Note note;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private NoteService noteService;
    private AppUser appUser;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        appUser = new AppUser( "test@gmail.com", "pass");
        appUserService.signUpUser(appUser);

        note = new Note("noteTitle", "myNotes", ZonedDateTime.now(), appUser);
        appUser.setLoggedIn(true);

        noteService.saveNotes(appUser.getId(), note);
    }

    @Test
    void saveNotes() throws Exception {

        MvcResult result = this.mockMvc.perform(post("/api/v1/note/{userId}",appUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect( status().isCreated())
                .andReturn();

        System.out.println(note.getId());

    }

    @Test
    void editNote() throws Exception {

        this.mockMvc.perform(put("/api/v1/note/{noteId}", note.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void notesAssociatedWithUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/note/{userId}", appUser.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteNote() throws Exception {
        this.mockMvc.perform(delete("/api/v1/note/{noteId}", note.getId()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}

