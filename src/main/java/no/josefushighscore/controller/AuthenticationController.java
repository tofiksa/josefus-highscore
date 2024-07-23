package no.josefushighscore.controller;

import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.model.User;
import no.josefushighscore.service.APIResponse;
import no.josefushighscore.service.AuthenticationService;
import no.josefushighscore.service.JwtService;
import no.josefushighscore.util.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationService authenticationService;

    Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginUserDto data) throws AuthenticationException {

        User authenticatedUser = authenticationService.authenticate(data);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/register")
    public ResponseEntity registerNewUserAccount(@RequestBody UserDto accountDto) throws BadRequestException {

        APIResponse apiResponse = new APIResponse();
        LOG.info(String.valueOf(accountDto));
        authenticationService.signup(accountDto);
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("User registered successfully");

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

