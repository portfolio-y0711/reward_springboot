package com.portfolioy0711.api.controllers._unit._1_controllers;
import com.portfolioy0711.api.controllers.EventController;
import com.portfolioy0711.api.services.EventService;
import com.portfolioy0711.api.typings.ActionType;
import com.portfolioy0711.api.typings.EventDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import static com.portfolioy0711.api.typings.ActionType.*;
import static com.portfolioy0711.api.typings.EventType.REVIEW;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collection;

public class EventControllerTest {
    private EventController eventController;

    private EventService eventService;

    @Before
    public void setUp() {
        eventService = Mockito.mock(EventService.class);
        eventController = new EventController(eventService);
    }

    @Test
    public void postEventTest() {
        EventDto eventDto = new EventDto(
                REVIEW,
                ADD,
                "240a0658-dc5f-4878-9831-ebb7b26687772",
                "좋아요",
                new String[] {
                        "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
                        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
                },
                "3ede0ef2-92b7-4817-a5f3-0c575361f745",
                "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        );
        eventController.postEvent(eventDto);
        Collection<Invocation> invocations = Mockito.mockingDetails(eventService).getInvocations();
        verify(eventService, times(1)).route(any());
    }
}
