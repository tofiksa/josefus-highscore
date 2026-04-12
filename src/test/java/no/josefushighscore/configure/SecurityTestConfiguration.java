package no.josefushighscore.configure;

import no.josefushighscore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@TestConfiguration
@ActiveProfiles("test")
public class SecurityTestConfiguration {

    @Autowired
    JwtAuthenticationFilter jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(jwtTokenProvider, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User();
        basicUser.setUsername("basicUser");
        basicUser.setPassword("basicUser");
        basicUser.setRoles(Arrays.asList("ROLE_ANONYMOUS"));

        return new InMemoryUserDetailsManager(basicUser);
    }
}
