package no.josefushighscore.configure;

import no.josefushighscore.model.User;
import no.josefushighscore.security.jwt.JwtSecurityConfigurer;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@TestConfiguration
public class SecurityTestConfiguration {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/register/**").hasRole("ANONYMOUS")
                .antMatchers("/auth/**").hasRole("ANONYMOUS").anyRequest().authenticated().and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));

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


