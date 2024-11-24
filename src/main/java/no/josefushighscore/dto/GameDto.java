package no.josefushighscore.dto;

import no.josefushighscore.model.Score;

import java.time.LocalDateTime;

public class GameDto {

    private Long gameId;
    private Long userId;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime gameEndTime;
    private Score score;

    public GameDto(Score score, Long gameId, Long userId, String username, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime gameEndTime) {
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

    public LocalDateTime getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(LocalDateTime gameEndTime) {
        this.gameEndTime = gameEndTime;
    }
}
