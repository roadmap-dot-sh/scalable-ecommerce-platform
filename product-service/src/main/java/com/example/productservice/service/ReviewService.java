package com.example.productservice.service;

import com.example.productservice.dto.request.CreateReviewRequest;
import com.example.productservice.dto.response.ReviewResponse;
import com.example.productservice.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductService productService;

    public ReviewResponse addReview(String productId, CreateReviewRequest request) {
        Review review = Review.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .images(request.getImages())
                .verifiedPurchase(request.isVerifiedPurchase())
                .build();
        productService.addReview(productId, review);
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .userName(review.getUserName())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .images(review.getImages())
                .verifiedPurchase(review.isVerifiedPurchase())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
