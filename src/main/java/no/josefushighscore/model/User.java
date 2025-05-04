package no.josefushighscore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.josefushighscore.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="`user`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`user_id`")
    Long userId;

    @NotEmpty
    @Column(name="`username`", unique = true)
    private String username;

    @NotEmpty
    @Column(name="`password`", unique = true)
    private String password;

    @NotEmpty
    @Column(name="`email`", unique = true)
    private String email;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @Column(name="`supabase_id`", unique = true)
    private UUID supabase_id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "roles")
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(name="`last_signed_in`")
    private LocalDateTime lastSignedIn;

    public LocalDateTime getLastSignedIn() {
        return lastSignedIn;
    }

    public void setLastSignedIn(LocalDateTime lastSignedIn) {
        this.lastSignedIn = lastSignedIn;
    }

    public UUID getSupabase_id() {
        return supabase_id;
    }

    public void setSupabase_id(UUID supabase_id) {
        this.supabase_id = supabase_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(this.getUsername());
        userDto.setFirstname(this.getFirstname());
        userDto.setLastname(this.getLastname());
        userDto.setEmail(this.getEmail());
        userDto.setLastSignedIn(this.getLastSignedIn());
        return userDto;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}