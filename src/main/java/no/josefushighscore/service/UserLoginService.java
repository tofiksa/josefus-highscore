package no.josefushighscore.service;

import no.josefushighscore.exception.EmailExistsException;
import no.josefushighscore.model.User;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserLoginService {
    @Autowired
    UserRegister users;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;


    public Object login(User loginRequestDTO) {

        String username = loginRequestDTO.getUsername();
        String pwd = loginRequestDTO.getPassword();

        String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));

        return model;
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

    public boolean emailExist(String email) {
        return users.findByEmail(email).isPresent();
    }
}
