package no.josefushighscore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.Score;
import no.josefushighscore.model.User;
import no.josefushighscore.register.GameRegister;
import no.josefushighscore.register.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ScoreService {

    private final GameRegister gameRegister;
    private final UserRegister userRegister;

    private final Logger LOG = LoggerFactory.getLogger(ScoreService.class);

    public ScoreService (GameRegister gameRegister, UserRegister userRegister) {
        this.gameRegister = gameRegister;
        this.userRegister = userRegister;
    }

    public ObjectNode getTotalScoreForUser (String username) {

        List<Game> getAllGamesForUser = this.gameRegister.findByUser_UserId(getUserId(username));
        Stream<Score> allGamesUserHasScoreOn = getAllGamesForUser.stream()
                .map(Game::getScoreId)
                .filter(Objects::nonNull)     // Filter out null Score objects
                .distinct();

        Long totalScore = allGamesUserHasScoreOn
                .mapToLong(score -> score.getScore() != null ? score.getScore() : 0L)  // Handle null scores
                .sum();
        LOG.info("This player scored {} points for player {}", totalScore, username);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("totalScore", totalScore);
        response.put("gameStats",getHighestAndLowestScores(getAllGamesForUser));

        return response;
    }

    private Long getUserId (String username) {
        Optional<User> getUserId = this.userRegister.findByUsername(username);
        return getUserId.orElseThrow().getUserId();
    }

    public ObjectNode getHighestAndLowestScores(List<Game> getAllGamesForUser) {

        Stream<Score> scores = getAllGamesForUser.stream()
                .map(Game::getScoreId)
                .filter(Objects::nonNull)     // Filter out null Score objects
                .distinct();

        LongSummaryStatistics stats = scores
                .filter(score -> score != null)
                .map(Score::getScore)
                .filter(score -> score != null)
                .mapToLong(Long::longValue)
                .filter(score -> score >= 0)
                .summaryStatistics();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("highestScore", stats.getCount() > 0 ? stats.getMax() : 0L);
        response.put("lowestScore", stats.getCount() > 0 ? stats.getMin() : 0L);

        return response;
    }


}
