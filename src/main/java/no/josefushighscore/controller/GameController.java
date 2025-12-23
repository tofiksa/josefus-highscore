package no.josefushighscore.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.josefushighscore.dto.ScoreDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.exception.InvalidJwtAuthenticationException;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.Score;
import no.josefushighscore.service.GameService;
import no.josefushighscore.service.RankService;
import no.josefushighscore.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RankService rankService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get game details", description = "Retrieve game details for the authenticated user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"gameId\": 1, \"userId\": 1, \"username\": \"user1\", \"createdAt\": \"2023-10-01T12:00:00\", \"updatedAt\": \"2023-10-01T12:00:00\", \"gameEndTime\": \"2023-10-01T12:30:00\", \"score\": { \"score\": 100 } }"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/id")
    public ResponseEntity<Game> getGameId(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(gameService.getGameDetails(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Post score", description = "Submit a new score for the authenticated user", responses = {
            @ApiResponse(responseCode = "200", description = "Score submitted successfully", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"score\": 100, \"createdAt\": \"2023-10-01T12:00:00\", \"game_id\": 1 }"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/score")
    public ResponseEntity<Score> postScore(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ScoreDto scoreDto) throws InvalidJwtAuthenticationException {
        return ok(gameService.updateScore(userDetails.getUsername(), scoreDto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get total score", description = "Retrieve the total score for the authenticated user", responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"totalScore\": 1000 }"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/score")
    public ResponseEntity<ObjectNode> getTotalScoreForUser(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(scoreService.getTotalScoreForUser(userDetails.getUsername()));
    }

    @Secured("ROLE_ANONYMOUS")
    @Operation(summary = "Get top players", description = "Retrieve the top ten players", responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[ { \"username\": \"user1\", \"totalScore\": 1000 }, { \"username\": \"user2\", \"totalScore\": 900 } ]"))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/ranking")
    public ResponseEntity<ObjectNode> getTopTenPlayers() throws BadRequestException {
        return ok(rankService.getTopTenPlayers());
    }
}