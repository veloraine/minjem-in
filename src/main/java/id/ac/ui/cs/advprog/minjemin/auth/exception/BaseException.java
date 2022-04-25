package id.ac.ui.cs.advprog.minjemin.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends Exception {
    private final HttpStatus status;

    public BaseException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }
}
