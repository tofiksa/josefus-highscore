package no.josefushighscore.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    //validity in milliseconds
    //private long validityInMs = 3600000; // 1h
    private long validityInMs = 520000; // 5m

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getValidityInMs() {
        return validityInMs;
    }

    public void setValidityInMs(long validityInMs) {
        this.validityInMs = validityInMs;
    }
}

