package vn.taidung.springsocial.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginUserRequest {

    @NotBlank(message = "email is required")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 3, max = 72)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
