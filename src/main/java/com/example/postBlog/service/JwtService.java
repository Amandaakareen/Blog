package com.example.postBlog.service;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String tokenJwt(Long id){
        String string = "YW1hbmRhR29zdG9zYUxpbmRhTWVsaG9yRXNwb3NhRU1lbGhvck1hZVRhbWJlbQ==";
        SecretKey key = Keys.hmacShaKeyFor(string.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().setSubject(id.toString()).signWith(key).compact();
        return jwt;
    }

    public void checkToken(String jwt){
        String string = "YW1hbmRhR29zdG9zYUxpbmRhTWVsaG9yRXNwb3NhRU1lbGhvck1hZVRhbWJlbQ==";
        SecretKey key = Keys.hmacShaKeyFor(string.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> parseClaimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt.split(" ")[1]);
    }

    

}
