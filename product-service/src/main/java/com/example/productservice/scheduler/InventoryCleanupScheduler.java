package com.example.productservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryCleanupScheduler {

    @Scheduled(cron = "0 0 3 * * *")
    public void nightlyInventoryAudit() {
        log.debug("Inventory audit placeholder — hook metrics or reconciliation here");
    }
}
