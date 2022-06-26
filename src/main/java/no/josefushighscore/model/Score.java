package no.josefushighscore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="`score`")
@Data
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="`score_id`")
    Long scoreId;

    @Column(name="`score`")
    Long score;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name="`created_at`")
    LocalDate createdAt;

    @Column(name="`updated_at`")
    LocalDate updatedAt;

    public Score(Long score, User user, Long scoreId) {
        this.score = score;
        this.user = user;
        this.scoreId = scoreId;
    }


}
