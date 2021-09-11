package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModReviewActionHandler implements ActionHandler {
    private EventDatabase eventDatabase;

    public ModReviewActionHandler(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Override
    public void handleEvent(Object event) {
        ReviewEventDto eventInfo = (ReviewEventDto) event;
        UserModel userModel = eventDatabase.getUserModel();
    }

}
