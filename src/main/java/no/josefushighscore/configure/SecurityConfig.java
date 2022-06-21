package no.josefushighscore.configure;

import no.josefushighscore.security.jwt.JwtSecurityConfigurer;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

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
                .antMatchers("/register/**").permitAll()
                .antMatchers("/auth/**").permitAll().anyRequest().authenticated().and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));

        return http.build();
    }
}

