
package no.josefushighscore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.User;
import no.josefushighscore.register.GameRegister;
import no.josefushighscore.register.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RankService {

    private final GameRegister gameRegister;
    private final UserRegister userRegister;
    private final ObjectMapper mapper = new ObjectMapper();

    private final Logger LOG = LoggerFactory.getLogger(RankService.class);

    public RankService(GameRegister gameRegister, UserRegister userRegister) {
        this.gameRegister = gameRegister;
        this.userRegister = userRegister;
    }

    public ObjectNode getTopTenPlayers() {
        List<User> allPlayers = getAllPlayers();

        List<ObjectNode> playerScores = allPlayers.parallelStream()
                .map(player -> {
                    Long totalScore = calculateTotalScore(player);
                    ObjectNode playerNode = mapper.createObjectNode();
                    playerNode.put("username", player.getUsername());
                    playerNode.put("totalScore", totalScore);
                    return playerNode;
                })
                .sorted(Comparator.comparingLong(node -> -node.get("totalScore").asLong()))
                .limit(10)
                .collect(Collectors.toList());

        ArrayNode topTenArray = mapper.createArrayNode();
        topTenArray.addAll(playerScores);

        ObjectNode response = mapper.createObjectNode();
        response.set("topTenPlayers", topTenArray);

        return response;
    }

    private Long calculateTotalScore(User player) {
        List<Game> games = gameRegister.findByUser_UserId(player.getUserId());
        return games.parallelStream()
                .map(Game::getScoreId)
                .filter(Objects::nonNull)
                .mapToLong(score -> score.getScore() != null ? score.getScore() : 0L)
                .sum();
    }

    private List<User> getAllPlayers() {
        return userRegister.findAll();
    }
}
