package no.josefushighscore.controller;

import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.service.APIResponse;
import no.josefushighscore.service.UserLoginService;
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
    UserLoginService loginService;

    Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/signin")
    public ResponseEntity<LoginUserDto> signin(@RequestBody LoginUserDto data) throws AuthenticationException {

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(loginService.login(data));

        return new ResponseEntity<>(loginService.login(data), HttpStatus.OK);
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/register")
    public ResponseEntity registerNewUserAccount(@RequestBody UserDto accountDto) throws BadRequestException {

        APIResponse apiResponse = new APIResponse();
        LOG.info(String.valueOf(accountDto));
        loginService.registerNewUserAccount(accountDto);
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("User registered successfully");

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

