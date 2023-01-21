package com.ezinne.note;

import com.ezinne.appUser.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Builder
@Table(name = "note")
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String myNotes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;





//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "app_user_id" )
//    private AppUser appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyNotes() {
        return myNotes;
    }

    public void setMyNotes(String myNotes) {
        this.myNotes = myNotes;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return myNotes.equals(note.myNotes) && appUser.equals(note.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myNotes, appUser);
    }
}
