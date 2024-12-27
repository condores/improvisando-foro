package com.dako.forohub.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dako.forohub.infra.exceptions.TokenCreationException;
import com.dako.forohub.infra.exceptions.TokenVerificationException;
import com.dako.forohub.user.domain.User;

@Service
public class TokenService {

    private static final String ISSUER = "auth0";

    @Value("${api.token.security}")
    private String secretKey;

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is generated
     * @return the generated JWT token
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Error creating JWT token", exception);
        }
    }

    /**
     * Generates the expiration date for the token.
     *
     * @return the expiration date as an Instant
     */
    public Instant generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-05:00"));
    }

    /**
     * Extracts the subject from the given JWT token.
     *
     * @param token the JWT token
     * @return the subject of the token
     * @throws TokenVerificationException if the token is invalid
     */
    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String subject = decodedJWT.getSubject();

            if (subject == null) {
                throw new TokenVerificationException("Invalid token: subject is null");
            }

            return subject;
        } catch (JWTVerificationException exception) {
            throw new TokenVerificationException("Invalid token", exception);
        }
    }
}