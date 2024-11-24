package no.josefushighscore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="`game`")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="`game_id`")
    Long gameId;

    @ManyToOne
    @JoinColumn(name = "`scoreId`")
    private Score scoreId;

    @OneToOne
    @JoinColumn(name = "`userId`")
    private User user;

    @Column(name="`created_at`")
    LocalDateTime createdAt;

    @Column(name="`updated_at`")
    LocalDateTime updatedAt;

    @Column(name="`game_end_time`")
    LocalDateTime gameEndTime;

    public Game() {

    }

    public LocalDateTime getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(LocalDateTime gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public Game(User user) {
        this.user = user;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Score getScoreId() {
        return scoreId;
    }

    public void setScoreId(Score scoreId) {
        this.scoreId = scoreId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
