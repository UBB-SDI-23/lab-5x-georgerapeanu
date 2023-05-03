package com.example.app.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.app.exceptions.AppException;
import com.example.app.secrets.JWTSecretManager;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class JWTUtils {
    public static DecodedJWT decodeRegisterToken(String token) throws AppException{
        Algorithm algorithm = Algorithm.HMAC256(JWTSecretManager.getSecret());
        JWTVerifier verifier = JWT
                .require(algorithm)
                .withSubject("register-code")
                .build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException("Invalid token for activate user");
        }
        return decodedJWT;
    }

    public static String getUserHandleFromAuthHeader(String header) throws AppException{
        if(!header.startsWith("Bearer ")) {
            throw new AppException("Invalid token");
        }
        String token = header.substring(new String("Bearer ").length());
        Algorithm algorithm = Algorithm.HMAC256(JWTSecretManager.getSecret());
        JWTVerifier verifier = JWT
                .require(algorithm)
                .withSubject("auth")
                .build();
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException("Invalid token for auth user");
        }
        return decodedJWT.getClaim("user_handle").asString();
    }

    public static String getRegisterToken(String user_handle) {
        Algorithm algorithm = Algorithm.HMAC256(JWTSecretManager.getSecret());
        return JWT.create()
                .withSubject("register-code")
                .withClaim("user_handle", user_handle)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addMinutes(new Date(), 10))
                .sign(algorithm);
    }

    public static String getLoginToken(String user_handle) {
        Algorithm algorithm = Algorithm.HMAC256(JWTSecretManager.getSecret());
        return JWT.create()
                .withSubject("auth")
                .withClaim("user_handle", user_handle)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addMinutes(new Date(), 10))
                .sign(algorithm);
    }
}
