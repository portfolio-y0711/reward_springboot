package com.portfolioy0711.api.data.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = { "placeId", "name", "country", "bonusPoint" })
public class Place {
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

}

