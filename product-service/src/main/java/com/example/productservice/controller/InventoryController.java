package com.example.productservice.controller;

import com.example.productservice.dto.request.InventoryUpdateRequest;
import com.example.productservice.dto.response.InventoryResponse;
import com.example.productservice.service.InventoryService;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class InventoryController {

    private final ProductService productService;
    private final InventoryService inventoryService;

    @PatchMapping("/{productId}/inventory")
    public void updateInventory(
            @PathVariable String productId,
            @Valid @RequestBody InventoryUpdateRequest request) {
        productService.updateInventory(productId, request);
    }

    @GetMapping("/{productId}/inventory-record")
    public InventoryResponse getRecord(@PathVariable String productId) {
        return inventoryService.getByProductId(productId);
    }

    @PostMapping("/{productId}/inventory/adjust")
    public InventoryResponse adjust(
            @PathVariable String productId,
            @RequestParam int delta) {
        return inventoryService.adjust(productId, delta);
    }
}
