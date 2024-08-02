package no.josefushighscore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreDto {

    @JsonProperty("score")
    private String score;
    @JsonProperty("gameId")
    private String gameId;

    // Constructors
    public ScoreDto() {
        // Default constructor
    }

    public ScoreDto(String score, String gameId) {
        this.score = score;
        this.gameId = gameId;
    }

    public String getScore() {
        return score;
    }


    public String getGameId() {
        return gameId;
    }



}

