package com.example.cartservice.client.fallback;

import com.example.cartservice.client.dto.ProductSnapshot;

public final class ProductServiceFallback {

    private ProductServiceFallback() {
    }

    public static ProductSnapshot notFound(String productId) {
        ProductSnapshot p = new ProductSnapshot();
        p.setId(productId);
        p.setActive(false);
        return p;
    }

    public static ProductSnapshot unavailable(String productId, Exception e) {
        ProductSnapshot p = new ProductSnapshot();
        p.setId(productId);
        p.setName("unknown");
        p.setActive(false);
        return p;
    }
}
