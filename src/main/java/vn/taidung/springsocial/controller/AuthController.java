package vn.taidung.springsocial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.taidung.springsocial.model.request.ActivateUserRequest;
import vn.taidung.springsocial.model.request.RegisterUserRequest;
import vn.taidung.springsocial.model.response.RegisterUserResponse;
import vn.taidung.springsocial.service.UserService;
import vn.taidung.springsocial.util.annotation.ApiMessage;

@RestController
@RequestMapping("/v1/authentication")
@Validated
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ApiMessage("Register user")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = this.userService.registerUserHandler(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/user/activate")
    @ApiMessage("Activate user")
    public ResponseEntity<Void> activate(@Valid @RequestBody ActivateUserRequest request) {
        this.userService.activateUser(request);
        return ResponseEntity.noContent().build();
    }
}
