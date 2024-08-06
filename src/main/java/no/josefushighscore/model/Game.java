package no.josefushighscore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    LocalDate createdAt;

    @Column(name="`updated_at`")
    LocalDate updatedAt;

    @Column(name="`game_end_time`")
    LocalDate gameEndTime;

    public Game() {

    }

    public LocalDate getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(LocalDate gameEndTime) {
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
