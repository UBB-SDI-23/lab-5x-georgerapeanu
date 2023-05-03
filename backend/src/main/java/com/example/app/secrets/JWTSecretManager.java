package com.example.app.secrets;

public class JWTSecretManager {
    public static String getSecret() {
        return System.getenv("JWT_SECRET");
    }
}
