package com.example.cartservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Placeholder for merging anonymous session carts with authenticated user carts.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartSyncService {

    public void mergeAnonymousIntoUser(String anonymousId, String userId) {
        log.debug("Cart merge skipped (anonymousId={}, userId={})", anonymousId, userId);
    }
}
