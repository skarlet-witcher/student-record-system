package cc.orangejuice.srs.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4202481345119828971L;

    public UserNotFoundException(Long id) {
        this("Could not find user with id '%s'", id);
    }

    public UserNotFoundException(String name) {
        this("Could not find user with name '%s'", name);
    }

    public UserNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
