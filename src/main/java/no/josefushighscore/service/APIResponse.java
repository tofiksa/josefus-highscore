package no.josefushighscore.service;

import no.josefushighscore.model.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class APIResponse {

    private HttpStatus status;
    private Object data;
    private Errors errors;
    private Object message;

    public APIResponse() {
        this.status = HttpStatus.OK;
    }

    public APIResponse(HttpStatus httpStatus, String message, Errors s) {
        this.message = message;
        this.errors = s;
        this.setStatus(httpStatus);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
