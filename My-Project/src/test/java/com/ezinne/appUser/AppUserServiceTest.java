package com.ezinne.appUser;

import com.ezinne.note.Note;
import com.ezinne.note.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Disabled
class AppUserServiceTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AppUser appUser;

    @BeforeEach
    public void setUp() {
        appUser = AppUser.builder()
                .id(1L)
                .firstName("Ezinne")
                .lastName("Chime")
                .email("chidi@gmail.com")
                .password("password")
                .build();
        appUserRepository.save(appUser);
    }

    @Test
    void testSignUpUserSuccessful() {

        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(appUser.getPassword())).thenReturn("hashed_password");

        bCryptPasswordEncoder.encode(appUser.getPassword());

         String result = appUserService.signUpUser(appUser);


         verify(appUserRepository, times(2)).save(any());
         verify(appUserRepository).findByEmail(anyString());
         verify(bCryptPasswordEncoder, times(2)).encode(anyString());

         assertEquals(String.format("user has signed up with id: %d %n FirstName: Ezinne%n LastName: Chime%n Email: chidi@gmail.com%n",
                 appUser.getId()),  result);
         assertThat(appUser).hasFieldOrPropertyWithValue("firstName", "Ezinne");
         assertThat(appUser).hasFieldOrPropertyWithValue("lastName", "Chime");
         assertThat(appUser).hasFieldOrPropertyWithValue("email", "chidi@gmail.com");
    }


    @Test
    void signUpUserUnsuccessful() {

        when(appUserRepository.findByEmail(anyString())).thenReturn(Optional.of(appUser));

        String result = appUserService.signUpUser(appUser);
        assertEquals("user with chidi@gmail.com already exists",result);
        }

    @Test
    public void testLoginUserSuccess() {

        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        when(bCryptPasswordEncoder.matches(appUser.getPassword(), appUser.getPassword())).thenReturn(true);


        String result = appUserService.loginUser(appUser);
        assertEquals("User is successfully logged in, user can now save notes", result);
    }

    @Test
    public void testLoginUserIncorrectPassword() {

        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        when(bCryptPasswordEncoder.matches(appUser.getPassword(), appUser.getPassword())).thenReturn(false);

        String result = appUserService.loginUser(appUser);
        assertEquals("Incorrect password", result);
    }

    @Test
    public void testLoginUserNeedsToSignUp() {

        when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.empty());

        String result = appUserService.loginUser(appUser);
        assertEquals("user needs to sign up", result);
    }

    @Test
    public void testDeleteUserSuccess() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setAppUser(appUser);

        List<Note> notes = noteRepository.findAll();
        notes.add(note1);

        appUser.setNotes(notes);

        when(appUserRepository.findById(appUser.getId())).thenReturn(Optional.of(appUser));

        String result = appUserService.deleteUser(appUser.getId());

        verify(appUserRepository).delete(any(AppUser.class));
        assertEquals("AppUser with 1 has been deleted", result);
    }

}