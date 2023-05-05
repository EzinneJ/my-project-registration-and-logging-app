package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByAppUser(Long appUserId);
}
