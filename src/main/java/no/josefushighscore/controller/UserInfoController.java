package no.josefushighscore.controller;

import no.josefushighscore.dto.GameDto;
import no.josefushighscore.exception.InvalidJwtAuthenticationException;
import no.josefushighscore.service.GameService;
import no.josefushighscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController()
public class UserInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(userService.getUserDetails(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/games")
    public ResponseEntity<Page<GameDto>> totalGames(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Page<GameDto> games = gameService.getAllGames(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(games);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/score")
    public ResponseEntity totalScore(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(userService.getUserDetails(userDetails.getUsername()));
    }


}
