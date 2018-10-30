package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.DAO;
import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static com.company.sailorsmarketplace.dto.AllEventParams.Builder.allEventParamsDto;

public class EventService implements IEventService {

    @Inject
    private Database database;

    @Inject
    private DAO<Event> eventDAO;

    @Override
    public User addUserToEvent(Long userId, Long eventId) {
        User user = database.getById(userId);
        Event event = eventDAO.getById(eventId);

        event.getUsers().add(user);

        if (user.getEvents() == null) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            user.setEvents(events);
        } else {
            user.getEvents().add(event);
        }

        eventDAO.update(event);
        database.update(user);

        return user;
    }

    @Override
    public AllEventParams createEvent(CreateUpdateEventParams params) {
        User owner = database.getById(params.chargeUserId);
        Event event = new Event(
                params.name,
                params.description,
                params.place,
                params.dateStart,
                params.dateEnd,
                owner);

        event.getUsers().add(owner);

        Event createdEvent = eventDAO.save(event);

        AllEventParams eventParams = allEventParamsDto()
                .eventId(createdEvent.getEventId())
                .name(createdEvent.getName())
                .description(createdEvent.getDescription())
                .place(createdEvent.getPlace())
                .dateStart(createdEvent.getDateStart())
                .dateEnd(createdEvent.getDateEnd())
                .chargeUser(createdEvent.getChargeUser())
                .users(createdEvent.getUsers())
                .build();

        owner.getEvents().add(createdEvent);
        database.update(owner);

        return eventParams;
    }

    @Override
    public boolean deleteEvent(Long eventId) {
        Event event = eventDAO.getById(eventId);

        event.setChargeUser(null);
        event.setUsers(null);

        eventDAO.delete(event);
        return eventDAO.getById(eventId) == null;
    }

    @Override
    public Event updateEvent(Long eventId) {
        return null;
    }

    @Override
    public void deleteUserFromEvent(Long userId, Long eventId) {
        User user = database.getById(userId);
        Event event = eventDAO.getById(eventId);

        event.getUsers().remove(user);
        eventDAO.update(event);
    }
}
