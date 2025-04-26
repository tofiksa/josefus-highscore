package no.josefushighscore.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import no.josefushighscore.register.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${spring.jwt.refresh-expiration-time}")
    private long refreshTokenExpiration;

    @Autowired
    private UserRegister userRegister;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setHeaderParam("typ","JWT")
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("roles",userRegister.findByUsername(userDetails.getUsername()).orElseThrow(
                                () -> new UsernameNotFoundException("Username " + userDetails.getUsername() + " not found")).getRoles())
                .claim("email", userRegister.findByUsername(userDetails.getUsername()).orElseThrow( () -> new UsernameNotFoundException("Username " + userDetails.getUsername() + " not found")).getEmail())
                .claim("name", getFullname(userDetails))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private String getFullname (UserDetails userDetails) {
        return userRegister.findByUsername(userDetails.getUsername()).orElseThrow( () -> new UsernameNotFoundException("Username " + userDetails.getUsername() + " not found")).getFirstname() + " " + userRegister.findByUsername(userDetails.getUsername()).orElseThrow( () -> new UsernameNotFoundException("Username " + userDetails.getUsername() + " not found")).getLastname();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}