package com.example.productservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateReviewRequest {

    @NotBlank
    private String userId;

    private String userName;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String title;

    @NotBlank
    private String comment;

    private List<String> images;
    private boolean verifiedPurchase;
}
