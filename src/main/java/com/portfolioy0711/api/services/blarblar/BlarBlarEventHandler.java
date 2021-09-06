package com.portfolioy0711.api.services.blarblar;

import com.portfolioy0711.api.services.blarblar.actions.A_ActionHandler;
import com.portfolioy0711.api.services.blarblar.actions.B_ActionHandler;
import com.portfolioy0711.api.services.blarblar.actions.C_ActionHandler;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.dto.BlarBlarEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class BlarBlarEventHandler {
    @Autowired
    private ApplicationContext context;

    private ActionHandler handler;

    public void route (BlarBlarEventDto event) {
        Map<String, ActionHandler> routes = new HashMap<>();
        routes.put("ADD", (ActionHandler) context.getBean(A_ActionHandler.class));
        routes.put("MOD", (ActionHandler) context.getBean(B_ActionHandler.class));
        routes.put("DEL", (ActionHandler) context.getBean(C_ActionHandler.class));

        routes.get(event.getAction()).handleEvent(event);
    }
}
