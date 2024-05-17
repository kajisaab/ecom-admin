package com.kajisaab.ecommerce.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private Environment env;

    public String extractUserEmail(String token) {
        return extractUserEmail(token, env.getProperty("spring.env.token.accessSecretKey"));
    };

    public String extractUserEmail(String token, String secretKey){
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    public <T> T extractClaim(String token, String secretKey, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secretKey){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, env.getProperty("spring.env.token.accessSecretKey"), Long.parseLong(Objects.requireNonNull(env.getProperty("spring.env.token.expires-in"))));
    }

    public String generateToken(UserDetails userDetails, String secretKey, Long expiration){
        return generateToken(new HashMap<>(), secretKey, expiration, userDetails );
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            String secretKey,
            Long expiration,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    };

    public boolean validateToken(String token,String secretKey, UserDetails userDetails) {
        final String userName = extractUserEmail(token, secretKey);
        return userName.equals(userDetails.getUsername());
    }

    /**
     * Checks the token is valid or not as well as check for the expiry as well.
     * @param token Token from client
     * @param userDetails userDetails extracted from the token
     * @return true if the token is valid and not expired and vice-versa.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, env.getProperty("spring.env.token.accessSecretKey"), userDetails);
    }

    public boolean isTokenValid(String token, String secretKey, UserDetails userDetails){
        final String userName = extractUserEmail(token, secretKey);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }

    private boolean isTokenExpired(String token, String secretKey){
        return extractExpiration(token, secretKey).before(new Date());
    }

    private Date extractExpiration(String token, String secretKey){
        return extractClaim(token, secretKey, Claims::getExpiration);
    }



    private Key getSignInKey(String secretKey){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
