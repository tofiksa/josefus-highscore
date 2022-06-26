package no.josefushighscore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="`game`")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="`game_id`")
    Long gameId;

    @ManyToOne
    @JoinColumn(name = "scoreId")
    private Score score;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name="`created_at`")
    LocalDate createdAt;

    @Column(name="`updated_at`")
    LocalDate updatedAt;

    public Game(Score score, User user, Long gameId) {
        this.score = score;
        this.user = user;
        this.gameId = gameId;
    }

}
