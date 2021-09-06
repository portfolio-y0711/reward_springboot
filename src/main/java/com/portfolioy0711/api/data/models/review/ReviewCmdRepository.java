package com.portfolioy0711.api.data.models.review;

import com.portfolioy0711.api.data.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCmdRepository extends JpaRepository<Review, String> { }
