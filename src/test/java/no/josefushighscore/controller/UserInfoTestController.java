package no.josefushighscore.controller;

import no.josefushighscore.configure.JwtAuthenticationFilter;
import no.josefushighscore.configure.SecurityTestConfiguration;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
//@WebMvcTest(controllers = UserInfoController.class)
@Import(SecurityTestConfiguration.class)
@ActiveProfiles("test")
public class UserInfoTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtAuthenticationFilter jwtTokenProvider;

    @Test
    @DisplayName("Test get userinfo")
    @WithUserDetails("basicUser")
    public void testGetUserinfo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/me"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("basicUser"));
    }
}
