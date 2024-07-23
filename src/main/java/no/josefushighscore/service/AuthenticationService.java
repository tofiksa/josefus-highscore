package no.josefushighscore.service;

import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.EmailExistsException;
import no.josefushighscore.exception.UsernameExistsException;
import no.josefushighscore.model.User;
import no.josefushighscore.register.UserRegister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {


    private final UserRegister userRepository;


    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRegister userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User signup(UserDto input) {
        User user = new User();

        if (emailExist(input.getEmail())) {
            throw new EmailExistsException("Epost finnes allerede!");
        } else if(usernameExist(input.getUsername())) {
            throw new UsernameExistsException("Brukernavn finnes allerede!");
        } else {

            user.setUsername(input.getUsername());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setEmail(input.getEmail());
            user.setFirstname(input.getFirstname());
            user.setLastname(input.getLastname());
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            user.setRoles(roles);

            userRepository.save(user);
        }
        return user;
    }

    public User authenticate(LoginUserDto input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username: " + input.getUsername() + " not found"));
    }

    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}