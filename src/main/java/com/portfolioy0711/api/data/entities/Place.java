package com.portfolioy0711.api.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Place {
    @Id
    String placeId;
    String name;
    String country;
    Integer bonusPoint;
}

