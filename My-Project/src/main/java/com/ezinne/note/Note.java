package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "note")
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Note implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    private String noteTitle;
    private String myNotes;
    private ZonedDateTime timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    public Note(String noteTitle, String myNotes, AppUser appUser) {
        this.noteTitle = noteTitle;
        this.myNotes = myNotes;
        this.appUser = appUser;
        this.timestamp = ZonedDateTime.now();
    }

    public Note( String noteTitle, String myNotes, ZonedDateTime timestamp, AppUser appUser) {
        this.noteTitle = noteTitle;
        this.myNotes = myNotes;
        this.appUser = appUser;
        this.timestamp = ZonedDateTime.now();
    }

        @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return noteTitle.equals(note.noteTitle) && myNotes.equals(note.myNotes) && appUser.equals(note.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteTitle, myNotes, appUser);
    }
}
