package com.portfolioy0711.api.controllers;

import com.portfolioy0711.api.services.ReviewService;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Api(tags = "Review")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "/reviews/{reviewId}", method = GET)
    public ReviewResponse getReview(
            @ApiParam(value = "review id",
            required = true, example = "240a0658-dc5f-4878-9831-ebb7b26687772")
            @PathVariable String reviewId) {
        ReviewResponse response = reviewService.fetchReviewByReviewId(reviewId);
        return response;
    }
}
