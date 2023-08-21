package com.smartChart.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// JwtService
@Service
public class JwtService {

    private static final String SECRET_KEY = "XUSMhedg8DLHCk/ArcQGfGpQfYrBMpifzjUe1nq+D3tm4WVgXrJl2pxyVPccZDosjqZA8ZVvNb4dAD6evkfrFVVh5/b2PQ54GCbrrzoBuccbAMsz63ASB88C9d4PzpdGs2xsbpUvh/ODF2WmRvHxwtZde55NDHfn/q5MJ32vAQQdCJam1Zez2dTXjEo9qta1m21m4+i/dpyzZZcKqzN/T64vVDzzysqXxuRaZ4J94RnvveLe/cFpL3hG8KZMTtdNEjL0dfsyo93tDV0pZswI5LsHjig1pHJzwnuYtZZ36WpF5dd14UIA2qIIai0YJlfv3V/hl5hqrfro3aMBCx+8BMJvUz8HxRIKnstQfWgvjhY=\n";
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject); // subject = email, or userName

    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }



    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }




    // generate token, extraClaims, userDetails
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // issue date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInkey(),SignatureAlgorithm.HS256)
                .compact();  // generate and return token
    }


    // username with token is the same with userDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token ) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())  // to create and decode a token
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
