package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;

@Entity
//@Table(name = "participations", schema = "smarket", catalog = "")
public class ParticipationsEntity {
    private EventsEntity eventsByEventId;
    private UsersEntity usersByUserId;

    @Id
    private long id;

    @ManyToOne(targetEntity = EventsEntity.class)
    @JoinColumn(name = "event_id", nullable = false)
    public EventsEntity getEventsByEventId() {
        return eventsByEventId;
    }

    public void setEventsByEventId(EventsEntity eventsByEventId) {
        this.eventsByEventId = eventsByEventId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
