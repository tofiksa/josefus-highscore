package no.josefushighscore.controller;

import no.josefushighscore.dto.UserDto;
import no.josefushighscore.model.User;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import no.josefushighscore.service.UserLoginService;
import no.josefushighscore.util.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRegister users;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginService loginService;

    Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @PreAuthorize("hasRole('ANONYMOUS')")
    @PostMapping("/signin")
    public ResponseEntity<APIResponse> signin(@RequestBody UserDto data) throws AuthenticationException {

        APIResponse apiResponse = loginService.login(data);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PreAuthorize("hasRole('ANONYMOUS')")
    @PostMapping("/register")
    public User registerNewUserAccount(@RequestBody UserDto accountDto) { //throws EmailExistsException {
        /*if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + accountDto.getEmail());
        }*/

        LOG.info("[REGISTER USER] " + accountDto.toString());
        User user = new User();
        user.setFirstname(accountDto.getFirstname());
        user.setLastname(accountDto.getLastname());
        user.setUsername(accountDto.getUsername());

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");

        user.setRoles(roles);

        return users.save(user);
    }
}

