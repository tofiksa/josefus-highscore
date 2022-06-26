package no.josefushighscore.exception;

import no.josefushighscore.service.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

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
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setError("Wrong username/password!");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleEmailExistsException(EmailExistsException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setError("Email already exists!");

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}

