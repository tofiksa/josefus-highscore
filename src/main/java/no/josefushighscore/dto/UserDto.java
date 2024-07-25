package no.josefushighscore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import no.josefushighscore.model.User;


public class UserDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @NotBlank
    @JsonProperty("email")
    private String email;

    @JsonProperty("fullname")
    private String fullname;



    public UserDto(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public UserDto(String username, String firstname, String lastname, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public UserDto() {
        
    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());

        return dto;
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

    public void setEmail(String email) {
        this.email = email;
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

