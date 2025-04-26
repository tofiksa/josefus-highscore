package no.josefushighscore.service;

import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.exception.EmailExistsException;
import no.josefushighscore.exception.UsernameExistsException;
import no.josefushighscore.model.User;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.util.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {


    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRegister userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

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

    public LoginResponse generateTokens(UserDetails userDetails) {
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return loginResponse;
    }
    public LoginResponse refreshToken(String refreshToken) throws BadRequestException {
        try {
            // Extract username and validate refresh token
            String username = jwtService.extractUsername(refreshToken);
            if (username == null) {
                throw new BadRequestException("Invalid refresh token", null);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isRefreshTokenValid(refreshToken)) {
                throw new BadRequestException("Expired or invalid refresh token", null);
            }

            // Generate new tokens
            return generateTokens(userDetails);
        } catch (Exception e) {
            // Log the specific error for debugging
            LOG.error("Refresh token error: {}", e.getMessage(), e);
            throw new BadRequestException("JWT signature verification failed: " + e.getMessage(), (List<Error>) e);
        }
    }

    public boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}