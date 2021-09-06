package com.portfolioy0711.api.data.models.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
