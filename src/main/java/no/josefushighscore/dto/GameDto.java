package no.josefushighscore.dto;

import no.josefushighscore.model.Score;

import java.time.LocalDate;

public class GameDto {

    private Long gameId;
    private Long userId;
    private String username;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate gameEndTime;
    private Score score;

    public GameDto(Score score, Long gameId, Long userId, String username, LocalDate createdAt, LocalDate updatedAt, LocalDate gameEndTime) {
        this.gameId = gameId;
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.gameEndTime = gameEndTime;
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(LocalDate gameEndTime) {
        this.gameEndTime = gameEndTime;
    }
}
