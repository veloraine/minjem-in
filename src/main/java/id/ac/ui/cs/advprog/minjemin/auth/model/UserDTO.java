package id.ac.ui.cs.advprog.minjemin.auth.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String username;

    private String password;

    private String passwordConfirm;

    private String roles;
}
