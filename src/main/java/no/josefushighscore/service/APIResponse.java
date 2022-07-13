package no.josefushighscore.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class APIResponse {

    private HttpStatus status;
    private Object data;
    private Object error;
    private Object message;

    public APIResponse() {
        this.status = HttpStatus.OK;
    }

    public APIResponse(HttpStatus httpStatus, String message, String s) {
        this.message = message;
        this.error = s;
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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
