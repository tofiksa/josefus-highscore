package no.josefushighscore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ScoreDto {

    @JsonProperty("score")
    @NotNull
    @PositiveOrZero
    private Long score;

    @JsonProperty("gameId")
    @NotNull
    private Long gameId;

    public ScoreDto() {
        // Default constructor
    }

    public ScoreDto(Long score, Long gameId) {
        this.score = score;
        this.gameId = gameId;
    }

    public Long getScore() {
        return score;
    }

    public Long getGameId() {
        return gameId;
    }
}

