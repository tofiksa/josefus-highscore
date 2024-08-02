package no.josefushighscore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "`score`")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`score_id`")
    private Long scoreId;

    @Column(name = "`score`", nullable = false)
    private Long score;

    @Column(name = "`created_at`", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "`updated_at`", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="`game_id`", unique = true)
    private Long game_id;

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    // Constructors
    public Score() {
        // Default constructor
    }

    public Score(Long score, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.score = score;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Score{" +
                "scoreId=" + scoreId +
                ", score=" + score +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", game_id=" + game_id +
                '}';
    }
}
