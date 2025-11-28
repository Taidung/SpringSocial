package vn.taidung.springsocial.util.errors;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import vn.taidung.springsocial.model.response.ErrorResponse;
import vn.taidung.springsocial.model.response.RestResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGenericException(HttpServletRequest request, Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setErrors(error);
        res.setMessage("Exception occurs...");

        LOGGER.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        LOGGER.error(ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            error.addError(fieldError.getDefaultMessage());
        });

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setErrors(error);
        res.setMessage("Validation failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponse<Object>> handleConstraintViolationException(HttpServletRequest request,
            ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(ex.getMessage());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setErrors(error);
        res.setMessage("Validation failed");

        LOGGER.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handlePostNotFoundException(HttpServletRequest request,
            NotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(ex.getMessage());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setErrors(error);
        res.setMessage("Not found");

        LOGGER.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<RestResponse<Object>> handleConflictException(HttpServletRequest request,
            ConflictException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(ex.getMessage());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setErrors(error);
        res.setMessage("Resource conflict");

        LOGGER.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestResponse<Object>> handleAuthenticationException(HttpServletRequest request,
            Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(Instant.now());
        error.setPath(request.getServletPath());
        error.addError(ex.getMessage());

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setErrors(error);
        res.setMessage("Unauthorized");

        LOGGER.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
}
