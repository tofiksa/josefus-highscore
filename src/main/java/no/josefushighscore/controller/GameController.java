package no.josefushighscore.controller;

import no.josefushighscore.dto.ScoreDto;
import no.josefushighscore.exception.InvalidJwtAuthenticationException;
import no.josefushighscore.service.GameService;
import no.josefushighscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/game")
public class GameController {


    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/id")
    public ResponseEntity getGameId(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(gameService.getGameDetails(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/score")
    public ResponseEntity postScore(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ScoreDto scoreDto) throws InvalidJwtAuthenticationException {
        return ok(gameService.updateScore(userDetails.getUsername(), scoreDto));
    }
}
