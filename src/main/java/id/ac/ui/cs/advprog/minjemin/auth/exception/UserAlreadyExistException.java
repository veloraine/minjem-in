package id.ac.ui.cs.advprog.minjemin.auth.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "Username sudah terdaftar sebelumnya!");
    }
}
