package no.josefushighscore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import no.josefushighscore.model.User;

import java.time.LocalDateTime;


public class UserDto {

    @JsonProperty("username")
    @NotBlank
    private String username;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 8)
    private String password;

    @JsonProperty("firstname")
    @NotBlank
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank
    private String lastname;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("lastSignedIn")
    private LocalDateTime lastSignedIn;

    public UserDto(String username, String password, String firstname, String lastname, String email, LocalDateTime lastSignedIn) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.lastSignedIn = lastSignedIn;
    }

    public UserDto(String username, String firstname, String lastname, String email, LocalDateTime lastSignedIn) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.lastSignedIn = lastSignedIn;
    }

    public UserDto() {

    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setLastSignedIn(user.getLastSignedIn());

        return dto;
    }

    public LocalDateTime getLastSignedIn() {
        return lastSignedIn;
    }

    public void setLastSignedIn(LocalDateTime lastSignedIn) {
        this.lastSignedIn = lastSignedIn;
    }

    public String getFullname() {
        return firstname + " " + lastname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}