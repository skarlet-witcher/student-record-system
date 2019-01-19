package cc.orangejuice.srs.auth.config;

import cc.orangejuice.srs.auth.util.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Autowired
    public EntryPointUnauthorizedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        log.info(authException.getMessage());
        Response msg = new Response();
        Object jwtErr = request.getAttribute("jwterror");
        if (jwtErr != null) {
            msg.failed(jwtErr.toString());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            msg.failed("please login");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(msg));
    }
}
