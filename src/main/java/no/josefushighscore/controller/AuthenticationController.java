package no.josefushighscore.controller;

import jakarta.validation.Valid;
import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.model.Errors;
import no.josefushighscore.service.APIResponse;
import no.josefushighscore.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserLoginService loginService;

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/signin")
    public ResponseEntity<LoginUserDto> signin(@RequestBody LoginUserDto data) throws AuthenticationException {

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(loginService.login(data));

        return new ResponseEntity<>(loginService.login(data), HttpStatus.OK);
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/register")
    public ResponseEntity registerNewUserAccount(@Valid @RequestBody UserDto accountDto) throws BadRequestException {

        APIResponse apiResponse = new APIResponse();
        HashMap<String, String> errors = new HashMap<>();

        try {
            loginService.registerNewUserAccount(accountDto);
            apiResponse.setStatus(HttpStatus.CREATED);
            apiResponse.setMessage("User registered successfully");
        } catch (Exception e) {
            errors.put("login", e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setErrors(new Errors(errors,null));
        }
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

