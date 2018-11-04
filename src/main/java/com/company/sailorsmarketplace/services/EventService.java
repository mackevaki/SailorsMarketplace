package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.EventRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.company.sailorsmarketplace.exceptions.EventNotFoundException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.google.inject.Inject;

import static com.company.sailorsmarketplace.dto.AllEventParams.Builder.allEventParamsDto;

public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Inject
    public EventService(final UserRepository userRepository, final EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public void addUserToEvent(final Long userId, final Long eventId) {
        final User user = userRepository.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final Event event = eventRepository.getById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        event.addUser(user);

//        if (user.getEvents() == null) {
//            List<Event> events = new ArrayList<>();
//            events.add(event);
//            user.setEvents(events);
//        } else {
//            user.getEvents().add(event);
//        }

        eventRepository.update(event);
        //userRepository.update(user);

        //return user;
    }

    public AllEventParams createEvent(final CreateUpdateEventParams params) {
        final User owner = userRepository.getById(params.chargeUserId).orElseThrow(() -> new UserNotFoundException(params.chargeUserId));
        final Event event = new Event(
                params.name,
                params.description,
                params.place,
                params.dateStart,
                params.dateEnd,
                owner)
                .addUser(owner); ///////////

        final Event createdEvent = eventRepository.save(event);

        return allEventParamsDto()
                .eventId(createdEvent.getEventId())
                .name(createdEvent.getName())
                .description(createdEvent.getDescription())
                .place(createdEvent.getPlace())
                .dateStart(createdEvent.getDateStart())
                .dateEnd(createdEvent.getDateEnd())
                .chargeUser(createdEvent.getChargeUser())
                .users(createdEvent.getUsers())
                .build();

//        owner.getEvents().add(createdEvent);
//        userRepository.update(owner);
//
//        return eventParams;
    }

    public void deleteEvent(Long eventId) { // not final????
        eventRepository.getById(eventId).ifPresent(eventRepository::delete);
//
//        event.setChargeUser(null);
//        event.setUsers(null);
//
//        eventRepository.delete(event);
//        return eventRepository.getById(eventId) == null;
    }

    public Event updateEvent(Long eventId) { // not final ????
        return null;
    }

    public void deleteUserFromEvent(final Long userId, final Long eventId) {
        final User user = userRepository.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final Event event = eventRepository.getById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        event.removeUser(user);
        eventRepository.update(event);
    }
}
