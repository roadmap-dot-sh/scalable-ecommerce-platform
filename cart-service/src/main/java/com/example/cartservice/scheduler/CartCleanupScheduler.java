package com.example.cartservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartCleanupScheduler {

    @Scheduled(cron = "0 0 4 * * *")
    public void cleanupPlaceholder() {
        log.debug("Cart cleanup job — implement stale-cart deletion if needed");
    }
}
