package no.josefushighscore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import no.josefushighscore.model.Game;
import no.josefushighscore.model.Score;
import no.josefushighscore.model.User;
import no.josefushighscore.register.GameRegister;
import no.josefushighscore.register.UserRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RankServiceTest {

    @Mock
    private GameRegister gameRegister;

    @Mock
    private UserRegister userRegister;

    @InjectMocks
    private RankService rankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTopTenPlayers() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("player1");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("player2");

        List<User> users = Arrays.asList(user1, user2);

        when(userRegister.findAll()).thenReturn(users);

        Score score1 = new Score();
        score1.setScore(100L);

        Score score2 = new Score();
        score2.setScore(200L);

        Game game1 = new Game();
        game1.setScoreId(score1);

        Game game2 = new Game();
        game2.setScoreId(score2);

        when(gameRegister.findByUser_UserId(1L)).thenReturn(Collections.singletonList(game1));
        when(gameRegister.findByUser_UserId(2L)).thenReturn(Collections.singletonList(game2));

        ObjectNode result = rankService.getTopTenPlayers();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedPlayer1 = mapper.createObjectNode();
        expectedPlayer1.put("username", "player2");
        expectedPlayer1.put("totalScore", 200L);

        ObjectNode expectedPlayer2 = mapper.createObjectNode();
        expectedPlayer2.put("username", "player1");
        expectedPlayer2.put("totalScore", 100L);

        assertEquals(2, result.get("topTenPlayers").size());
        assertEquals(expectedPlayer1, result.get("topTenPlayers").get(0));
        assertEquals(expectedPlayer2, result.get("topTenPlayers").get(1));
    }
}

