package no.josefushighscore.controller;

import no.josefushighscore.model.User;
import no.josefushighscore.service.APIResponse;
import no.josefushighscore.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserLoginService loginService;

    @PreAuthorize("hasRole('ANONYMOUS')")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody User data) throws AuthenticationException {

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(loginService.login(data));

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @PreAuthorize("hasRole('ANONYMOUS')")
    @PostMapping("/register")
    public ResponseEntity registerNewUserAccount(@Valid @RequestBody User accountDto) {

        APIResponse apiResponse = new APIResponse();
        try {
            loginService.registerNewUserAccount(accountDto);
            apiResponse.setStatus(HttpStatus.CREATED);
            apiResponse.setMessage("User registered successfully");
        } catch (Exception e) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setError(e.getMessage());
        }
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }
}

