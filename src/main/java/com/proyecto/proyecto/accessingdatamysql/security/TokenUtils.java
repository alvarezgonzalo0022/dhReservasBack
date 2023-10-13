package com.proyecto.proyecto.accessingdatamysql.security;

import java.util.*;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenUtils {

    private static final String ACCESS_TOKEN_SECRET = "DGSAFOGPSD789FASDUFHUSAJ12PKLJDASLKÑDSAKLND";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2592000L;


    public static String createToken(String nombre, String email, Collection<GrantedAuthority> authorities) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);


        List<String> authorityList = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .claim("authorities", authorityList)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            List<String> authorityNames = Optional.ofNullable(claims.get("authorities", List.class)).orElse(Collections.emptyList());
            Collection<GrantedAuthority> authorities = new HashSet<>();
            for (String authorityName : authorityNames) {
                authorities.add(new SimpleGrantedAuthority(authorityName));
            }

            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        } catch (JwtException e) {
            return null;
        }

    }

}
