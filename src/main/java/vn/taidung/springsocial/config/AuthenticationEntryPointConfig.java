package vn.taidung.springsocial.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.taidung.springsocial.model.response.ErrorResponse;
import vn.taidung.springsocial.model.response.RestResponse;

@Component
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public AuthenticationEntryPointConfig(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(authException.getCause().getMessage());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setErrors(error);
        res.setMessage("Invalid token");

        mapper.writeValue(response.getWriter(), res);
    }
}
