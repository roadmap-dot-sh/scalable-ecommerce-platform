package com.example.cartservice.client.fallback;

public final class UserServiceFallback {

    private UserServiceFallback() {
    }

    /**
     * When user-service is down, allow cart operations to avoid hard failure in dev.
     */
    public static boolean assumeExists() {
        return true;
    }
}
