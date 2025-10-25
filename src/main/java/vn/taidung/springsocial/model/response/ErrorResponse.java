package vn.taidung.springsocial.model.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private Instant timestamp;
    private String path;
    private List<String> errors = new ArrayList<>();

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

}
