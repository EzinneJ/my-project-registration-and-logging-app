package com.ezinne.appUser;

import com.ezinne.note.Note;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "appUser")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class AppUser implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "app_user_email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    private Boolean loggedIn;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Note> notes;

    public AppUser(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.loggedIn = false;
    }

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AppUser(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public AppUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
