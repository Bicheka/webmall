package com.bicheka.security;

public class SecurityConstants {

    public static final String SECRET_KEY = System.getenv("API_KEY"); //Your secret should always be strong (uppercase, lowercase, numbers, symbols) so that nobody can potentially decode the signature.
    public static final long TOKEN_EXPIRATION = 30 * 24 * 60 * 60 * 1000L; // 30 days in milliseconds
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token 
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/user/register"; // Public path that clients can use to register.
    public static final String GET_STORES = "/store/get_all_stores";
}
