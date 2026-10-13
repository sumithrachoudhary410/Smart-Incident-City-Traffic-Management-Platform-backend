package com.smartcity.platform.auth;

public record JwtAuthResponse(String accessToken, String tokenType, String username, String role) {
}
