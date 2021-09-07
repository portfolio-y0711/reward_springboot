package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class PlaceModel {
    @Autowired
    PlaceCmdRepository placeCmdRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Integer findBonusPoint(String placeId) {
       return 0;
    }

    public Place save(Place place) {
        return placeCmdRepository.save(place);
    }
}
