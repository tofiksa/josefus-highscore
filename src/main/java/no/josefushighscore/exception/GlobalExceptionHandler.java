package no.josefushighscore.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import no.josefushighscore.model.Errors;
import no.josefushighscore.service.APIResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",extractDetailedMessage(e));

        APIResponse apiResponse = new APIResponse();
        apiResponse.setErrors(new Errors(errors,null));
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    private String extractDetailedMessage(Throwable e) {
        final String message = e.getMessage();
        if (message == null) {
            return "";
        }
        final int tailIndex = StringUtils.indexOf(message, "; nested exception is");
        if (tailIndex == -1) {
            return message;
        }
        return StringUtils.left(message, tailIndex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationExceptions(
            MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        apiResponse.setErrors(new Errors(errors,null));
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBadRequestException(BadRequestException e){
        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setErrors(new Errors(errors,null));

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleAccessDeniedException(AuthenticationException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));
        LOG.info(e.getMessage());
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleBadCredentialsException(BadCredentialsException e){

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));
        LOG.info(e.getMessage());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleUsernameNotFoundException(UsernameNotFoundException e) {

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errors",e.getMessage());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setErrors(new Errors(errors,null));
        LOG.info(e.getMessage());

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

