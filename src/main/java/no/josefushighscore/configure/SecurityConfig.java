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
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    CorsFilter corsFilter;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .addFilterAfter(corsFilter, SecurityContextHolderAwareRequestFilter.class)
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources", "/v3/api-docs/*", "/v3/api-docs").hasRole("ANONYMOUS")
                .requestMatchers("/register/**").hasRole("ANONYMOUS")
                .requestMatchers("/auth/**").hasRole("ANONYMOUS").anyRequest().authenticated().and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));

        return http.build();
    }
}


