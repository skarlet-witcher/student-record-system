package cc.orangejuice.srs.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 1575607802430613939L;

    @Getter
    @Setter
    private String code = "";

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
