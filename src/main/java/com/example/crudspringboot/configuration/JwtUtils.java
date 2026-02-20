package com.example.crudspringboot.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret-key}") // permet de recuperer les valeurs misent dans application.properties
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private Long expirationTime;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) { // le subject c'est le username
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  // username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() { // permet d'avoir une clé secrète a partir de secretKey
        // byte[] keyBytes = secretKey.getBytes();
        //return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) &&  !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e)  {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return  extractExpirationDate(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResover ) {
        final Claims claims = extractAllClaims(token);
        return claimsResover.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())      // clé HMAC ou clé RSA
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
