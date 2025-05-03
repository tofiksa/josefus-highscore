package no.josefushighscore.util;

import java.time.LocalDateTime;

public class LoginResponse {

    private String token;

    private String refreshToken;

    private long expiresIn;
    
    private LocalDateTime lastSignedIn;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public LocalDateTime getLastSignedIn() {
        return lastSignedIn;
    }
    
    public void setLastSignedIn(LocalDateTime lastSignedIn) {
        this.lastSignedIn = lastSignedIn;
    }
}