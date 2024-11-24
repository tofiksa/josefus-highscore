package no.josefushighscore.service;

import no.josefushighscore.dto.GameDto;
import no.josefushighscore.dto.ScoreDto;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.Score;
import no.josefushighscore.model.User;
import no.josefushighscore.register.GameRegister;
import no.josefushighscore.register.ScoreRegister;
import no.josefushighscore.register.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GameService {

    private final GameRegister gameRegister;
    private final UserRegister userRegister;
    private final ScoreRegister scoreRegister;

    private final Logger LOG = LoggerFactory.getLogger(GameService.class);

    public GameService (GameRegister gameRegister, UserRegister userRegister, ScoreRegister scoreRegister) {
        this.gameRegister = gameRegister;
        this.userRegister = userRegister;
        this.scoreRegister = scoreRegister;
    }

    public Game getGameDetails(String username) {
        Optional<User> currentUser = userRegister.findByUsername(username);
        Game game = new Game();
        game.setUser(currentUser.orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found")));
        game.setCreatedAt(LocalDateTime.now());
        game.setGameEndTime(LocalDateTime.now());
        gameRegister.save(game);

        return game;
    }

    public Page<GameDto> getAllGames(String username, int page, int size) {

        Optional<User> currentUser = userRegister.findByUsername(username);
        Long userId = currentUser.orElseThrow( () -> new UsernameNotFoundException("Username " + username + " not found")).getUserId();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("gameId").descending());
        Page<Game> games = this.gameRegister.findByUser_UserId(userId,pageRequest);

        Page<GameDto> gameDTOPage = games.map(this::convertToGameDTO);

        LOG.info("size of listofGames {}", games.getSize());

        return gameDTOPage;
    }

    public Score updateScore(String username, ScoreDto scoreDto) {

        Optional<Game> currentGame = this.gameRegister.findByGameId(Long.valueOf(scoreDto.getGameId()));

        LOG.info("current game is {} for username: {}", currentGame.orElseThrow().getGameId(), username);
        Score score = new Score();
        score.setScore(Long.valueOf(scoreDto.getScore()));
        score.setCreatedAt(LocalDateTime.now());
        score.setGame_id(Long.valueOf(scoreDto.getGameId()));
        LOG.info("updating score: {} gameId: {}", scoreDto.getScore(), scoreDto.getGameId());
        this.scoreRegister.save(score);

        Game updatedGame = currentGame
                .map(game -> {
                    game.setScoreId(score);
                    return this.gameRegister.save(game);
                })
                .orElse(null);  // Or throw an exception, or handle the absence in another way

        if (updatedGame != null) {
            // Game was found and updated
        } else {
            // Game was not found
        }

        return score;
    }

    private GameDto convertToGameDTO(Game game) {
        return new GameDto(
                game.getScoreId(),
                game.getGameId(),
                game.getUser().getUserId(),
                game.getUser().getUsername(),
                game.getCreatedAt(),
                game.getUpdatedAt(),
                game.getGameEndTime()
        );
    }


}
