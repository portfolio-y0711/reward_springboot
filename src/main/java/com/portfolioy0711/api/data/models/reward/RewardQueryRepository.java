package com.portfolioy0711.api.data.models.reward;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RewardQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
