package no.josefushighscore.controller;

import no.josefushighscore.exception.InvalidJwtAuthenticationException;
import no.josefushighscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController()
public class UserInfoController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(userService.getUserDetails(userDetails.getUsername()));
    }
}
