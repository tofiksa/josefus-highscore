package no.josefushighscore.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import no.josefushighscore.service.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

    @ExceptionHandler
    public ResponseEntity handleException(Exception e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setError("Oops..Something went wrong!");
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setError(e.getErrors());

        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AuthenticationException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setError("Wrong username/password!");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleBadCredentialsException(BadCredentialsException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError("Wrong password!");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError("Username not found!");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }



    @ExceptionHandler
    public ResponseEntity handleSignatureException(SignatureException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError("JWT signature does not match locally computed signature. " +
                "JWT validity cannot be asserted and should not be trusted.");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleServletException(ServletException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError(e.getMessage());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError(e.getMessage());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleExpiredJwtException(ExpiredJwtException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError(e.getMessage());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }



}

