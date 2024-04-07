package no.josefushighscore.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import no.josefushighscore.model.Errors;
import no.josefushighscore.service.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(Exception e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",String.valueOf(e));

        APIResponse apiResponse = new APIResponse();
        apiResponse.setErrors(new Errors(errors,null));
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AuthenticationException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleBadCredentialsException(BadCredentialsException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e) {

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }



    @ExceptionHandler
    public ResponseEntity handleSignatureException(SignatureException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors","JWT signature does not match locally computed signature. " +
                "JWT validity cannot be asserted and should not be trusted.");

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleServletException(ServletException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleExpiredJwtException(ExpiredJwtException e){


        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }



}

