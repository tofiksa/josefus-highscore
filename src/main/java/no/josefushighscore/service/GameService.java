package no.josefushighscore.service;

import no.josefushighscore.dto.GameDto;
import no.josefushighscore.dto.ScoreDto;
import no.josefushighscore.exception.InvalidJwtAuthenticationException;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.Score;
import no.josefushighscore.model.User;
import no.josefushighscore.register.GameRegister;
import no.josefushighscore.register.ScoreRegister;
import no.josefushighscore.register.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @SuppressWarnings("null")
    public Page<GameDto> getAllGames(String username, int page, int size) {

        Optional<User> currentUser = userRegister.findByUsername(username);
        Long userId = currentUser.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found")).getUserId();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("gameId").descending());
        Page<Game> games = gameRegister.findByUser_UserIdAndScoreIdIsNotNull(userId, pageRequest);
        Objects.requireNonNull(games);

        // Filter out null games and games that do not include a score
        List<GameDto> validGameDtos = games.getContent().stream()
            .filter(game -> game.getScoreId() != null)
            .map(this::convertToGameDTO)
            .collect(Collectors.toList());
        Objects.requireNonNull(validGameDtos);

        Page<GameDto> gameDTOPage = new PageImpl<>(List.copyOf(validGameDtos), pageRequest, games.getTotalElements());

        LOG.info("size of listofGames {}", validGameDtos.size());

        return gameDTOPage;
    }

    public Score updateScore(String username, ScoreDto scoreDto) {

        User user = userRegister.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

        Game game = this.gameRegister.findByGameId(scoreDto.getGameId())
                .orElseThrow(() -> new InvalidJwtAuthenticationException("Game not found"));

        if (!game.getUser().getUserId().equals(user.getUserId())) {
            throw new InvalidJwtAuthenticationException("Game does not belong to user");
        }

        LOG.info("current game is {} for username: {}", game.getGameId(), username);
        Score score = new Score();
        score.setScore(scoreDto.getScore());
        LocalDateTime now = LocalDateTime.now();
        score.setCreatedAt(now);
        score.setUpdatedAt(now);
        score.setGame_id(scoreDto.getGameId());
        LOG.info("updating score: {} gameId: {}", scoreDto.getScore(), scoreDto.getGameId());
        this.scoreRegister.save(score);

        game.setScoreId(score);
        this.gameRegister.save(game);

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
