package id.ac.ui.cs.advprog.minjemin.auth.exception;

import org.springframework.http.HttpStatus;

public class UnmatchPasswordException extends BaseException {
    public UnmatchPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Password yang dimasukkan tidak sama!");
    }
}
