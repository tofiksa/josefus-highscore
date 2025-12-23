package no.josefushighscore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.RefreshTokenRequestDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.exception.BadRequestException;
import no.josefushighscore.model.User;
import no.josefushighscore.service.APIResponse;
import no.josefushighscore.service.AuthenticationService;
import no.josefushighscore.service.JwtService;
import no.josefushighscore.util.LoginResponse;
import jakarta.validation.Valid;
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
    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Generate new access token using refresh token", responses = {
        @ApiResponse(responseCode = "200", description = "New token generated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid refresh token")
    })
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        try {
            String refreshToken = request.getRefreshToken();
            // Validate refresh token and generate new access token
            LoginResponse response = authenticationService.refreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/signin")
    @Operation(summary = "Sign in a user", description = "Authenticate a user and return a JWT token", responses = {
        @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody LoginUserDto data) throws AuthenticationException {

        User authenticatedUser = authenticationService.authenticate(data);
        LoginResponse loginResponse = authenticationService.generateTokens(authenticatedUser);

        if (loginResponse.getToken() != null) {
            authenticationService.updateUserSignInTracking(authenticatedUser);
            LOG.info("User {} logged in successfully", authenticatedUser.getUsername());
        } else {
            LOG.error("Failed to log in user {}", authenticatedUser.getUsername());
        }

        return ResponseEntity.ok(loginResponse);
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account", responses = {
        @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<APIResponse> registerNewUserAccount(@Valid @RequestBody UserDto accountDto) throws BadRequestException {

        LOG.info(String.valueOf(accountDto));
        authenticationService.signup(accountDto);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setMessage("User registered successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }
}

