package no.josefushighscore.register;

import no.josefushighscore.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRegister  extends JpaRepository<Game,Long> {

    Optional<Game> findLatestByUser_UserId(Long userId);
    Optional<Game> findByGameId(Long gameId);
    Page<Game> findByUser_UserIdAndScoreIdIsNotNull(Long userId, Pageable pageable);
    List<Game> findByUser_UserId(Long userId);
}
