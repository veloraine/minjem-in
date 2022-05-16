package id.ac.ui.cs.advprog.minjemin.auth.exception;

import org.springframework.http.HttpStatus;

public class WhitespaceValueException extends BaseException {
    public WhitespaceValueException() {
        super(HttpStatus.BAD_REQUEST, "Mohon memasukkan nilai yang valid untuk username dan password!");
    }
}
