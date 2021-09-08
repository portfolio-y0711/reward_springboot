package com.portfolioy0711.api.services.review;
import com.portfolioy0711.api.services.review.actions.AddReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.DelReviewActionHandler;
import com.portfolioy0711.api.services.review.actions.ModReviewActionHandler;
import com.portfolioy0711.api.typings.ActionHandler;
import com.portfolioy0711.api.typings.EventHandler;
import com.portfolioy0711.api.typings.dto.ReviewEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ReviewEventActionRouter implements EventHandler {

    @Autowired
    private ApplicationContext context;

    Map<String, ActionHandler> routes;

    public ReviewEventActionRouter(ApplicationContext context) {
        this.context = context;
        this.routes = new HashMap<>();
        this.routes.put("ADD", (ActionHandler) context.getBean(AddReviewActionHandler.class));
        this.routes.put("MOD", (ActionHandler) context.getBean(ModReviewActionHandler.class));
        this.routes.put("DEL", (ActionHandler) context.getBean(DelReviewActionHandler.class));
    }

    public void addRoute(String path, ActionHandler handler) {
       this.routes.put(path, handler);
    }

    public void removeRoute(String path) {
        this.routes.remove(path);
    }

    public Map<String, ActionHandler> getRoute() {
        return this.routes;
    }

    public void route (Object event) {
        String action = ((ReviewEventDto) event).getAction();
        routes.get(action).handleEvent(event);
    }
}
