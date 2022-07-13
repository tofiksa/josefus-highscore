package no.josefushighscore.service;

import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.EmailExistsException;
import no.josefushighscore.model.User;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserLoginService {
    @Autowired
    UserRegister users;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;



    public LoginUserDto login(LoginUserDto loginRequestDTO) {

        String username = loginRequestDTO.getUsername();
        String pwd = loginRequestDTO.getPassword();
        String token = getJWTToken(username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        loginRequestDTO.setJwtToken(token);

        return loginRequestDTO;
    }

    private String getJWTToken(String username) {
        return jwtTokenProvider.createToken(username,
                users.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
    }

    public User registerNewUserAccount(User user) {

        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("Epost finnes allerede!");
        } else {
            //User user = new User();
            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            user.setFirstname(user.getFirstname());
            user.setLastname(user.getLastname());
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            user.setRoles(roles);

            users.save(user);
        }
        return user;
    }

    public UserDto registerNewUserAccount(UserDto userDto) {

        User user = new User();

        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException("Epost finnes allerede!");
        } else {

            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            user.setFirstname(user.getFirstname());
            user.setLastname(user.getLastname());
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            user.setRoles(roles);

            users.save(user);
        }
        return userDto;
    }

    public boolean emailExist(String email) {
        return users.findByEmail(email).isPresent();
    }

}
