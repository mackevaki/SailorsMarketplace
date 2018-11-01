package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.EventRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.Event;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.company.sailorsmarketplace.exceptions.EventNotFoundException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

import javax.inject.Inject;

import static com.company.sailorsmarketplace.dto.AllEventParams.Builder.allEventParamsDto;

public class EventService {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;

    @Inject
    public EventService(final UserRepository userRepo, final EventRepository eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public void addUserToEvent(final Long userId, final Long eventId) {
        final User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final Event event = eventRepo.getById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        event.addUser(user);
        eventRepo.update(event);
    }

    public AllEventParams createEvent(final CreateUpdateEventParams params) {
        final User owner = userRepo.getById(params.chargeUserId)
                .orElseThrow(() -> new UserNotFoundException(params.chargeUserId));
        final Event event =
                new Event(
                        params.name,
                        params.description,
                        params.place,
                        params.dateStart,
                        params.dateEnd,
                        owner
                )
                        .addUser(owner);

        final Event createdEvent = eventRepo.save(event);

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
    }

    public void deleteEvent(Long eventId) {
        eventRepo.getById(eventId).ifPresent(eventRepo::delete);
    }

    public Event updateEvent(Long eventId) {
        return null;
    }

    public void deleteUserFromEvent(final Long userId, final Long eventId) {
        final User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final Event event = eventRepo.getById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        event.removeUser(user);
        eventRepo.update(event);
    }
}
