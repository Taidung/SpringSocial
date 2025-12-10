package vn.taidung.springsocial.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.model.UserInvitation;
import vn.taidung.springsocial.model.request.ActivateUserRequest;
import vn.taidung.springsocial.model.request.RegisterUserRequest;
import vn.taidung.springsocial.model.response.RegisterUserResponse;
import vn.taidung.springsocial.model.response.UserResponse;
import vn.taidung.springsocial.repository.RoleRepository;
import vn.taidung.springsocial.repository.UserInvitationRepository;
import vn.taidung.springsocial.repository.UserRepository;
import vn.taidung.springsocial.util.errors.ConflictException;
import vn.taidung.springsocial.util.errors.NotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInvitationRepository userInvitationRepository;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();
    private final RoleRepository roleRepository;

    @Value("${app.invitation.ttl-seconds:259200}")
    private long invitationSeconds;

    @Value("${app.frontend.activate-url:http://localhost:8080/activate.html?token=}")
    private String activateBaseUrl;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
            UserInvitationRepository userInvitationRepository, EmailService emailService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userInvitationRepository = userInvitationRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
    }

    public UserResponse getUserHandler(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.userRepository.findByIdAndIsActiveTrue(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    public User getUserByEmailHandler(String email) throws NotFoundException {
        Optional<User> optionalUser = this.userRepository.findByEmailAndIsActiveTrue(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        return user;
    }

    @Transactional
    public RegisterUserResponse registerUserHandler(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(false);
        user.setRole(roleRepository.findByName("user").orElseThrow(() -> new NotFoundException("role not found")));
        userRepository.save(user);

        String rawToken = generateRawToken();
        byte[] hashedToken = hashToken(rawToken);

        UserInvitation invitation = new UserInvitation();
        invitation.setToken(hashedToken);
        invitation.setUserId(user.getId());
        invitation.setExpiry(Instant.now().plusSeconds(invitationSeconds));
        userInvitationRepository.save(invitation);

        String activationLink = activateBaseUrl + rawToken;
        try {
            emailService.sendActivationEmail(user.getEmail(), user.getUsername(), activationLink);
        } catch (MailException | MessagingException ex) {
            throw new RuntimeException("send activation email failed", ex);
        }

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new RegisterUserResponse(userResponse, rawToken);
    }

    @Transactional
    public void activateUser(ActivateUserRequest request) {
        byte[] hashedToken = hashToken(request.getToken());

        UserInvitation invitation = userInvitationRepository
                .findByTokenAndExpiryAfter(hashedToken, Instant.now())
                .orElseThrow(() -> new NotFoundException("invitation not found or expired"));

        User user = userRepository.findById(invitation.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (user.isActive()) {
            userInvitationRepository.delete(invitation);
            throw new ConflictException("user already active");
        }

        user.setActive(true);
        userRepository.save(user);
        userInvitationRepository.deleteByUserId(user.getId());
    }

    private String generateRawToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(token.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 not available", ex);
        }
    }
}
