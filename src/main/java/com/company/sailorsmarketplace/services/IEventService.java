package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParamsDto;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.company.sailorsmarketplace.requests.CreateEventRequest;

public interface IEventService {

    User addUserToEvent(Long userId, Long eventId);

    AllEventParamsDto createEvent(CreateUpdateEventParams params);

    void deleteEvent(Long eventId);

    Event updateEvent(Long eventId);

    void deleteUserFromEvent(Long userId, Long eventId);

}
