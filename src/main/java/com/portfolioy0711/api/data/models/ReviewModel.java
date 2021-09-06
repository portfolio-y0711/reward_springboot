package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.portfolioy0711.api.data.models.review.ReviewCmdRepository;
import com.portfolioy0711.api.data.models.review.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewModel {
    ReviewCmdRepository userCmdRepository;
    ReviewQueryRepository userQueryRepository;
}
