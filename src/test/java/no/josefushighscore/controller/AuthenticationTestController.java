package no.josefushighscore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.josefushighscore.configure.JwtAuthenticationFilter;
import no.josefushighscore.configure.SecurityTestConfiguration;
import no.josefushighscore.dto.LoginUserDto;
import no.josefushighscore.dto.UserDto;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthenticationController.class)
@Import(SecurityTestConfiguration.class)
@ActiveProfiles("test")
public class AuthenticationTestController {

    @MockBean
    AuthenticationService userLoginService;

    @MockBean
    JwtAuthenticationFilter jwtTokenProvider;

    @MockBean
    UserRegister userRegister;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test register new user")
    @WithAnonymousUser
    public void testRegisterNewUser() throws Exception {

        UserDto userDto = new UserDto("testuser", "setuppassword", "TestFirstname", "TestLastname", "test@test.com", LocalDateTime.now());

        //when(userLoginService.registerNewUserAccount(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Test signin")
    @WithUserDetails("basicUser")
    public void testSigninAndGetJWTToken() throws Exception {

        LoginUserDto userDto = new LoginUserDto("basicUser", "setuppassword","testjwt");

        //when(userLoginService.login(any(LoginUserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/auth/signin").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }





}
