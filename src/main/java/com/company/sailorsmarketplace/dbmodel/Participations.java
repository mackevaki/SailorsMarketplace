package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
//@Table(name = "participations", schema = "smarket", catalog = "")
public class Participations {
    private Event eventsByEventId;
    private User usersByUserId;

    @Id
    private long id;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name = "event_id", nullable = false)
    public Event getEventsByEventId() {
        return eventsByEventId;
    }

    public void setEventsByEventId(Event eventsByEventId) {
        this.eventsByEventId = eventsByEventId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(User usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
