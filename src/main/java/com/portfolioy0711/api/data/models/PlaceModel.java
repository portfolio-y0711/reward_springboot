package com.portfolioy0711.api.data.models;

import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.portfolioy0711.api.data.models.place.PlaceQueryRepository;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.data.models.user.UserQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceModel {
    PlaceCmdRepository userCmdRepository;
    PlaceQueryRepository userQueryRepository;
}
