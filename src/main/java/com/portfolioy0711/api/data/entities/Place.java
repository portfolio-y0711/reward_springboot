package com.portfolioy0711.api.data.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Place {
    @Id
    String placeId;
    String name;
    String country;
    Integer bonusPoint;

    @Builder
    public Place(String placeId, String name, String country, Integer bonusPoint) {
        this.placeId = placeId;
        this.name = name;
        this.country = country;
        this.bonusPoint = bonusPoint;
    }

}

