package com.ezinne.appUser;

import com.ezinne.note.Note;
import com.ezinne.note.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final NoteRepository noteRepository;

    public String signUpUser(AppUser appUser) {
        String email = appUser.getEmail();

        Optional<AppUser> userExists = appUserRepository.findByEmail(email);

        if (userExists.isPresent()) {
            return String.format("user with %s already exists", email);
        }

            setBCryptPasswordEncoder(appUser);

             appUser.setLoggedIn(false);
             appUserRepository.save(appUser);
            return String.format("user has signed up with id: %d %n FirstName: %s%n LastName: %s%n Email: %s%n",
                         appUser.getId(), appUser.getFirstName(), appUser.getLastName(), appUser.getEmail());
    }

    public String loginUser(AppUser appUser) {
        String email = appUser.getEmail();
        Optional<AppUser> emailExists = appUserRepository.findByEmail(email);

        if (emailExists.isEmpty() && !email.matches(appUserRepository.findByEmail(email).toString())) {
            return "user needs to sign up";
        }

        AppUser appUser1 = emailExists.get();
        String password = appUser1.getPassword();

        boolean matches = bCryptPasswordEncoder.matches(appUser.getPassword(), password);

        if (matches) {
            appUser1.setLoggedIn(true);
            appUserRepository.save(appUser1);
            return "User is successfully logged in, user can now save notes";
        }
        return "Incorrect password";
    }

        public String deleteUser(Long id) {
            Optional<AppUser> existingUser = appUserRepository.findById(id);
            if (existingUser.isEmpty()) {
                return "user not found";
            }
            AppUser appUser = existingUser.get();
            List<Note> notes = appUser.getNotes();

            if (notes != null && !notes.isEmpty()) {
                appUser.setNotes(null);
                appUserRepository.save(appUser);
                for (Note note : notes) {
                    noteRepository.deleteById(note.getId());
                }
            }
            appUserRepository.delete(appUser);
            return String.format("AppUser with %d has been deleted", id);
        }

        private void setBCryptPasswordEncoder (AppUser appUser){
            String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodePassword);
        }

    }
