package com.alas.gasenergiaacqua.auth;

import com.alas.gasenergiaacqua.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * Generates and handles JWT Tokens.
 * Uses a HMAC algorithm with a secret key to ensure token authenticity and integrity.
 */
@Component
public class JwtTokenProvider {
    private final SecretKey SECRET_KEY;

    public JwtTokenProvider(@Value("${jwt.secret}") final String secret) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a new token based off the object inside AuthorizationRequest.
     *
     * @param request the AuthorizationRequest object which contains the info needed to be put inside the token.
     * @return the new token
     */
    public String generateToken(final UserDTO request) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", request.getId());
        claims.put("userType", request.getUserTypeId());
        return createToken(claims, request.getEmail());
    }

    /**
     * Creates a token with the provided claims and the specified subject.
     *
     * @param claims a map of claims
     * @param subject token subject, an identifier
     * @return the actual JWT token
     */
    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Validates a token and verifies if it is expired.
     *
     * @param token the token you want to check
     * @return 'true' if the token is valid, 'false' otherwise
     */
    public boolean isValid(final String token) {
        final Claims claims = extractAllClaims(token);
        if (extractExpiration(token).equals(claims.getExpiration())) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * Verifies if the token has expired.
     *
     * @param token token
     * @return 'true' if the token is expired, 'false' otherwise
     */
    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Verifies if there is a token
     *
     * @param token token
     * @return 'false' if a token is present
     */
    public boolean isTokenEmpty(final String token) {
        return token.isBlank();
    }

    /**
     * Extracts expiration date from token
     *
     * @param token token
     * @return expiration date of token
     */
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts email from a token subject.
     *
     * @param token token
     * @return email
     */
    public String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts id from claims
     *
     * @param token token
     * @return id
     */
    public UUID extractIdFromClaims(final String token) {
        return UUID.fromString(extractClaim(token, claims -> claims.get("id", String.class)));
    }

    /**
     * Extracts a user type id
     *
     * @param token token
     * @return value of userType
     */
    public Integer extractUserTypeFromClaims(final String token) {
        return extractClaim(token, claims -> claims.get("userType", Integer.class));
    }

    /**
     * Extract a specific claim from the JWT token
     *
     * @param token          JWT token
     * @param claimsResolver function that defines how to extract the claims from the token
     * @param <R>            type of claim that needs to be extracted
     * @return the extracted claim
     */
    public <R> R extractClaim(final String token, Function<Claims, R> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a token
     *
     * @param token token
     * @return all the claims from the token
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}