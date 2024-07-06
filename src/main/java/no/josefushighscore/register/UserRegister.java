package no.josefushighscore.register;


import no.josefushighscore.dto.UserDto;
import no.josefushighscore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegister extends JpaRepository<User,Long> {
    @Query("SELECT NEW no.josefushighscore.dto.UserDto(u.username, u.firstname, u.lastname, u.email) " +
            "FROM User u WHERE u.username = :username")
    Optional<UserDto> getUserDetails(@Param("username") String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
