package com.portfolioy0711.api.services.review.actions;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.data.models.user.UserModel;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelReviewActionHandler implements ActionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private EventDatabase eventDatabase;

    public DelReviewActionHandler(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Override
    public void handleEvent(Object event) {
//        ReviewEventDto eventInfo = (ReviewEventDto) event;
//        logger.info(String.format("[EVENT: ReviewEventActionHandler (%s)] started process ========================START", eventInfo.getAction()));
//        UserModel userModel = eventDatabase.getUserModel();
    }

}
