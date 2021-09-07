package com.portfolioy0711.api._i11._1_databases;

import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.QPlace;
import com.portfolioy0711.api.data.entities.QUser;
import com.portfolioy0711.api.data.entities.User;
import com.portfolioy0711.api.data.models.PlaceModel;
import com.portfolioy0711.api.data.models.RewardModel;
import com.portfolioy0711.api.data.models.UserModel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlaceModelTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    UserModel userModel;

    @Autowired
    PlaceModel placeModel;

    @Autowired
    RewardModel rewardModel;

    @Test
    @Transactional
    public void saveTest() {
        QPlace qPlace = QPlace.place;
        String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
        Place expected = Place.builder()
                .bonusPoint(0)
                .country("호주")
                .name("멜번")
                .placeId(placeId)
                .build();
        placeModel.save(expected);

        Place result = query
                .select(qPlace)
                .from(qPlace)
                .where(qPlace.placeId.eq(placeId))
                .fetchOne();

        assertEquals(result.getPlaceId(), expected.getPlaceId());
    }

    @Test
    @Transactional
    public void findUserRewardPointTest() {
        QUser qUser = QUser.user;
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        User expected = new User(userId, "James", 4);
        userModel.save(expected);

        User result = query
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
        assertEquals(result.getRewardPoint(), expected.getRewardPoint());
    }

}
