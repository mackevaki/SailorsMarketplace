package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "smarket")
public class EventsEntity {
    private Integer eventId;
    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private byte[] place;
    private User usersByChargeUserId;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Collection<User> usersEntities = new ArrayList<>();
//    private Collection<User> participants;

    @Id
    @Column(name = "event_id", nullable = false)
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "date_start", nullable = true)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "date_end", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "place", nullable = true)
    public byte[] getPlace() {
        return place;
    }

    public void setPlace(byte[] place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventsEntity that = (EventsEntity) o;
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

    @ManyToOne
    @JoinColumn(name = "charge_user_id", referencedColumnName = "user_id", nullable = false)
    public User getUsersByChargeUserId() {
        return usersByChargeUserId;
    }

    public void setUsersByChargeUserId(User usersByChargeUserId) {
        this.usersByChargeUserId = usersByChargeUserId;
    }

//    @OneToMany(mappedBy = "eventsByEventId", targetEntity = ParticipationsEntity.class)
//    public Collection<ParticipationsEntity> getParticipationsByEventId() {
//        return participationsByEventId;
//    }
//
//    public void setParticipationsByEventId(Collection<ParticipationsEntity> participationsByEventId) {
//        this.participationsByEventId = participationsByEventId;
//    }
}
