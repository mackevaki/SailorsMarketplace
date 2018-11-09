package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Basic
    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_start")
    private Date dateStart;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date_end")
    private Date dateEnd;

    @Basic
    @Column(name = "place")
    private byte[] place;

    @ManyToOne//(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "charge_user_id", referencedColumnName = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "events_charge_user_id_user_id_fk"))
    private User chargeUser;

    @ManyToMany//(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "user_events")
    private List<User> users = new ArrayList<>();

    public Event() {}

    public Event(User chargeUser, String name, String description) {
        this.chargeUser = chargeUser;
        this.name = name;
        this.description = description;
    }

    public Event(String name, String description, byte[] place, Date dateStart, Date dateEnd, User chargeUser) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.place = place;
        this.chargeUser = chargeUser;
    }

    public Event addUser(final User user) {
        this.users.add(user);
        return this;
    }
    public Event removeUser(final User user) {
        this.users.remove(user);
        return this;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> eventParticipations) {
        this.users = eventParticipations;
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

    @Override
    public String toString() {
        return "Event{" +
            "eventId=" + eventId +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", dateStart=" + dateStart +
            ", dateEnd=" + dateEnd +
            ", place=" + Arrays.toString(place) +
            ", chargeUser=" + chargeUser +
            ", users=" + users +
            '}';
    }

    public User getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(User usersByChargeUserId) {
        this.chargeUser = usersByChargeUserId;
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
