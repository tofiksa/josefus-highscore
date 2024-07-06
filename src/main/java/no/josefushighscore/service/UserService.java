package no.josefushighscore.service;

import no.josefushighscore.dto.UserDto;
import no.josefushighscore.register.UserRegister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRegister userRepository;


    public UserService(UserRegister userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> getUserDetails(String username) {
        return this.userRepository.getUserDetails(username);
    }
}