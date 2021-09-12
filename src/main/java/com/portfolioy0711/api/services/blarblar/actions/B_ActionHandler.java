package com.portfolioy0711.api.services.blarblar.actions;

import com.portfolioy0711.api.data.EventDatabase;
import com.portfolioy0711.api.typings.ActionHandler;
import org.springframework.stereotype.Component;

@Component
public class B_ActionHandler implements ActionHandler {
    private final EventDatabase eventDatabase;

    public B_ActionHandler(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Override
    public void handleEvent(Object event) {

    }
}
