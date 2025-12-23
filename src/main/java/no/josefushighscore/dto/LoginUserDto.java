package no.josefushighscore.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class LoginUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private String jwtToken;

    public LoginUserDto() {
    }

    public LoginUserDto(String username, String password, String jwtToken) {
        this.username = username;
        this.password = password;
        this.jwtToken = jwtToken;
    }

    public LoginUserDto(Map<Object, Object> model) {
        this.username = (String) model.get("username");
        this.password = (String) model.get("password");
        this.jwtToken = (String) model.get("token");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
