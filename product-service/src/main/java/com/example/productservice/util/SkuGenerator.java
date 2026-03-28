package com.example.productservice.util;

import java.util.UUID;

public final class SkuGenerator {

    private SkuGenerator() {
    }

    public static String randomSku(String prefix) {
        String p = prefix != null && !prefix.isBlank() ? prefix + "-" : "SKU-";
        return p + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
