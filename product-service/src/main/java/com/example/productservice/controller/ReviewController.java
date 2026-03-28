package com.example.productservice.controller;

import com.example.productservice.dto.request.CreateReviewRequest;
import com.example.productservice.dto.response.ReviewResponse;
import com.example.productservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(
            @PathVariable String productId,
            @Valid @RequestBody CreateReviewRequest request) {
        return reviewService.addReview(productId, request);
    }
}
