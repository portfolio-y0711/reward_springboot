package com.portfolioy0711.api.data.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = { "placeId", "name", "country", "bonusPoint" })
public class Place extends Base {
    @Id
    @Column(name = "placeId")
    String placeId;
    String name;
    String country;
    Integer bonusPoint;

    @OneToMany(mappedBy = "place")
    List<Review> reviews = new ArrayList<Review>();

    @Builder
    public Place(String placeId, String name, String country, Integer bonusPoint) {
        this.placeId = placeId;
        this.name = name;
        this.country = country;
        this.bonusPoint = bonusPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return placeId.equals(place.placeId) &&
                name.equals(place.name) &&
                country.equals(place.country) &&
                bonusPoint.equals(place.bonusPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, name, country, bonusPoint);
    }
}

