package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "events", schema = "smarket")
public class Event {

    @Id
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Basic
    @Column(name = "description", nullable = true, length = 45)
    private String description;

    @Basic
    @Column(name = "date_start", nullable = true)
    private Date dateStart;

    @Basic
    @Column(name = "date_end", nullable = true)
    private Date dateEnd;

    @Basic
    @Column(name = "place", nullable = true)
    private byte[] place;

    @ManyToOne
    @JoinColumn(name = "charge_user_id", referencedColumnName = "user_id", nullable = false)
    private User userByChargeUserId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> eventParticipations = new ArrayList<>();

    public Event() {}

    public Event(User chargeUser, String name) {
        userByChargeUserId = chargeUser;
    }

    public Event(String name, String description, byte[] place, Date dateStart, Date dateEnd, User userByChargeUserId) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.place = place;
        this.userByChargeUserId = userByChargeUserId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public byte[] getPlace() {
        return place;
    }

    public void setPlace(byte[] place) {
        this.place = place;
    }

    public List<User> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<User> eventParticipations) {
        this.eventParticipations = eventParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event that = (Event) o;
        return Objects.equals(eventId, that.eventId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd) &&
                Arrays.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(eventId, name, description, dateStart, dateEnd);
        result = 31 * result + Arrays.hashCode(place);
        return result;
    }

    public User getUserByChargeUserId() {
        return userByChargeUserId;
    }

    public void setUserByChargeUserId(User usersByChargeUserId) {
        this.userByChargeUserId = usersByChargeUserId;
    }

//    @OneToMany(mappedBy = "eventsByEventId", targetEntity = Participations.class)
//    public Collection<Participations> getParticipationsByEventId() {
//        return participationsByEventId;
//    }
//
//    public void setParticipationsByEventId(Collection<Participations> participationsByEventId) {
//        this.participationsByEventId = participationsByEventId;
//    }
}
