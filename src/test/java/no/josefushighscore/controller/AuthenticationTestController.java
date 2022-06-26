package no.josefushighscore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.josefushighscore.configure.SecurityTestConfiguration;
import no.josefushighscore.model.User;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityTestConfiguration.class)
public class AuthenticationTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Test register new user")
    public void testRegisterNewUser() throws Exception {

        User user = new User();
        user.setUserId(Long.valueOf(1));
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setFirstname("TestFirstname");
        user.setLastname("TestLastname");
        user.setEmail("test@test.com");

        when(userLoginService.registerNewUserAccount(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test signin")
    public void testSignin() throws Exception {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        when(userLoginService.login(any(User.class))).thenReturn(user);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").exists());
        System.out.println(result.andReturn().getResponse().getContentAsString());

    }



}
