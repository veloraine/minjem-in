package id.ac.ui.cs.advprog.minjemin.auth.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "minjemin_user")
@Data
@NoArgsConstructor

public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    private String username;

    @Column
    private String password;

    @Transient
    private String passwordConfirm;

    @Column
    private boolean active;

    @Column
    private String roles;

    public User(String userName, String password, String roles) {
        this.username = userName;
        this.password = password;
        this.active = true;
        this.roles = roles;
    }
}
