package no.josefushighscore.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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



    @Operation(summary = "Get current user details", description = "Retrieve details of the currently authenticated user", responses = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetails.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(userService.getUserDetails(userDetails.getUsername()));
    }


    @Operation(summary = "Get all games", description = "Retrieve all games for the authenticated user", responses = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/games")
    public ResponseEntity<Page<GameDto>> totalGames(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Page<GameDto> games = gameService.getAllGames(userDetails.getUsername(), page, size);
        return ResponseEntity.ok(games);
    }


    @Operation(summary = "Get user rank", description = "Retrieve the rank of the authenticated user", responses = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDetails.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/rank")
    public ResponseEntity<?> getRank(@AuthenticationPrincipal UserDetails userDetails) throws InvalidJwtAuthenticationException {
        return ok(userService.getUserDetails(userDetails.getUsername()));
    }


}
