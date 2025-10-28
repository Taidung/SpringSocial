package vn.taidung.springsocial.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import vn.taidung.springsocial.model.User;
import vn.taidung.springsocial.model.response.UserResponse;
import vn.taidung.springsocial.repository.UserRepository;
import vn.taidung.springsocial.util.errors.NotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse getUserHandler(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("user not found");
        }
        User user = optionalUser.get();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }
}
