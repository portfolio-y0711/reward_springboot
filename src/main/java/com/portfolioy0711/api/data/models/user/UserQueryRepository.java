package com.portfolioy0711.api.data.models.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
