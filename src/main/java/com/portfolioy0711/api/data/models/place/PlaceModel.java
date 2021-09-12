package com.portfolioy0711.api.data.models.place;

import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.QPlace;
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlaceModel {
    private final PlaceCmdRepository placeCmdRepository;
    private final JPAQueryFactory query;

    public Integer findBonusPoint(String placeId) {
        return 0;
    }

    public Place save(Place place) {
        return placeCmdRepository.save(place);
    }

    public Place findPlaceByPlaceId(String placeId) {
        QPlace place = QPlace.place;
        return query
                .select(place)
                .from(place)
                .where(place.placeId.eq(placeId))
                .fetchOne();
    }

}
