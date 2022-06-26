package no.josefushighscore.controller;

import no.josefushighscore.configure.SecurityTestConfiguration;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import no.josefushighscore.service.UserLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserinfoTestController.class)
@Import(SecurityTestConfiguration.class)
public class UserinfoTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Test get userinfo")
    public void testGetUserinfo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
