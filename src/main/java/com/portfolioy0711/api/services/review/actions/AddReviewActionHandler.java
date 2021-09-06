package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.models.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.portfolioy0711.api.data.EventDatabase;


@Component
public class AddReviewActionHandler implements ActionHandler {

    @Autowired
    EventDatabase eventDatabase;

    @Override
    public void handleEvent(Object event) {
        ReviewEventDto eventInfo = (ReviewEventDto) event;
        UserModel userModel = eventDatabase.getUserModel();

    }
}
