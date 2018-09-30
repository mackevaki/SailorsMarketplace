package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.EventDao;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParamsDto;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static com.company.sailorsmarketplace.dto.AllEventParamsDto.Builder.allEventParamsDto;

@Singleton
public class EventService implements IEventService {

    @Inject
    Database database;

    EventDao eventDao;

    @Override
    public User addUserToEvent(Long userId, Long eventId) {
        User user = database.getById(userId);

        eventDao = new EventDao();
        Event event = eventDao.getById(eventId);

        event.getEventParticipations().add(user);
        if (user.getEvents() == null) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            user.setEvents(events);
        } else {
            user.getEvents().add(event);
        }

        eventDao.update(event);
        database.update(user);

        return user;
    }

    @Override
    public AllEventParamsDto createEvent(CreateUpdateEventParams params) {
        User owner = database.getById(params.chargeUserId);
        Event event = new Event(
                params.name,
                params.description,
                params.place,
                params.dateStart,
                params.dateEnd,
                owner);

        List<User> eventUsers = new ArrayList<>();
        eventUsers.add(owner);
        event.setEventParticipations(eventUsers);

        Event createdEvent = eventDao.save(event);
        AllEventParamsDto eventParams = allEventParamsDto()
                .eventId(createdEvent.getEventId())
                .name(createdEvent.getName())
                .description(createdEvent.getDescription())
                .place(createdEvent.getPlace())
                .dateStart(createdEvent.getDateStart())
                .dateEnd(createdEvent.getDateEnd())
                .chargeUser(createdEvent.getUserByChargeUserId())
                .users(createdEvent.getEventParticipations())
                .build();

//        if (owner.getEvents() == null) {
//            List<Event> events = new ArrayList<>();
//            events.add(createdEvent);
//            owner.setEvents(events);
//        } else {
            owner.getEvents().add(createdEvent);
//        }

        database.update(owner);

        return eventParams;
    }

    @Override
    public void deleteEvent(Long eventId) {

    }

    @Override
    public Event updateEvent(Long eventId) {
        return null;
    }

    @Override
    public void deleteUserFromEvent(Long userId, Long eventId) {

    }
}
