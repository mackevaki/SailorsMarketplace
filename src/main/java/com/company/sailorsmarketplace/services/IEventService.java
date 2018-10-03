package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;

public interface IEventService {

    User addUserToEvent(Long userId, Long eventId);

    AllEventParams createEvent(CreateUpdateEventParams params);

    void deleteEvent(Long eventId);

    Event updateEvent(Long eventId);

    void deleteUserFromEvent(Long userId, Long eventId);

}
