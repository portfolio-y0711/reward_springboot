package com.portfolioy0711.api.services;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.models.review.ReviewModel;
import com.portfolioy0711.api.typings.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final EventDatabase eventDatabase;

    public ReviewService(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    public ReviewResponse fetchReviewByReviewId(String reviewId) {
       ReviewModel reviewModel = eventDatabase.getReviewModel();
       return reviewModel.findReviewsByReviewId(reviewId);
    }
}
