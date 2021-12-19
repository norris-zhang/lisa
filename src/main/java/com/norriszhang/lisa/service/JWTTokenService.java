package com.norriszhang.lisa.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Service
public class JWTTokenService implements Clock, TokenService {
    private static final String DOT = ".";
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    private final String issuer;
    private final int expirationSec;
    private final int clockSkewSec;
    private final String secretKey;

    public JWTTokenService(@Value("${jwt.issuer:norriszhang}") String issuer,
                           @Value("${jwt.expiration-sec:86400}") int expirationSec,
                           @Value("${jwt.clock-skew-sec:300}") int clockSkewSec,
                           @Value("${jwt.secret:secret}") String secretKey) {
        this.issuer = issuer;
        this.expirationSec = expirationSec;
        this.clockSkewSec = clockSkewSec;
        this.secretKey = secretKey;
    }

    @Override
    public String permanent(Map<String, String> attributes) {
        return newToken(attributes, 0);
    }

    @Override
    public String expiring(Map<String, String> attributes) {
        return newToken(attributes, expirationSec);
    }

    @Override
    public Map<String, String> untrusted(String token) {
        final JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec);

        // See: https://github.com/jwtk/jjwt/issues/135
        token.substring(0, token.lastIndexOf(DOT));
        final String withoutSignature = substringBeforeLast(token, DOT) + DOT;
        return parseClaims(parser.parseClaimsJwt(withoutSignature).getBody());
    }

    @Override
    public Map<String, String> verify(String token) {
        JwtParser parser = Jwts.parser().requireIssuer(issuer).setClock(this).setAllowedClockSkewSeconds(clockSkewSec).setSigningKey(secretKey);

        return parseClaims(parser.parseClaimsJws(token).getBody());
    }

    private String newToken(final Map<String, String> attributes, final int expiresInSec) {
        LocalDateTime ldt = LocalDateTime.now();
        final Claims claims = Jwts.claims().setIssuer(issuer).setIssuedAt(toDate(ldt));
        if (expiresInSec > 0) {
            LocalDateTime expiresAt = ldt.plusSeconds(expiresInSec);
            claims.setExpiration(toDate(expiresAt));
        }
        claims.putAll(attributes);
        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compressWith(COMPRESSION_CODEC)
                   .compact();
    }

    private Map<String, String> parseClaims(Claims claims) {
        Map<String, String> map = new HashMap<>();
        try {
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                map.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    @Override
    public Date now() {
        return toDate(LocalDateTime.now());
    }

    private Date toDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
