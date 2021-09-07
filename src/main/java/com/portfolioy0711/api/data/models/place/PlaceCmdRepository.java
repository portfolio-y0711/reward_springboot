package com.portfolioy0711.api.data.models.place;

import com.portfolioy0711.api.data.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PlaceCmdRepository extends JpaRepository<Place, String> { }
