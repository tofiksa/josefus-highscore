package no.josefushighscore.register;

import no.josefushighscore.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRegister  extends JpaRepository<Game,Long> {

    Optional<Game> findLatestByUser_UserId(Long userId);
    Optional<Game> findByGameId(Long gameId);
}
