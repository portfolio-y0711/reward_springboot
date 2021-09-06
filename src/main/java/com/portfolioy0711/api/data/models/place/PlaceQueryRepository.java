package com.portfolioy0711.api.data.models.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlaceQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
