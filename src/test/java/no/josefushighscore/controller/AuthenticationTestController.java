package no.josefushighscore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.josefushighscore.configure.SecurityTestConfiguration;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import no.josefushighscore.service.UserLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@Import(SecurityTestConfiguration.class)
public class AuthenticationTestController {

    @MockBean
    UserLoginService userLoginService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;


    @Test
    @DisplayName("Test register new user")
    @WithAnonymousUser
    public void testRegisterNewUser() throws Exception {

        UserDto userDto = new UserDto("testuser", "setuppassword", "TestFirstname", "TestLastname", "test@test.com");

        when(userLoginService.registerNewUserAccount(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Test signin")
    @WithAnonymousUser
    public void testSigninAndGetJWTToken() throws Exception {

        UserDto userDto = new UserDto("testuser","testy",null,null,null);
        when(userLoginService.login(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }





}
