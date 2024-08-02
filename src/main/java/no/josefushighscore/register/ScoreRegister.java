package no.josefushighscore.register;

import no.josefushighscore.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRegister extends JpaRepository<Score,Long> {
}
