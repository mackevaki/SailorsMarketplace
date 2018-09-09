package com.company.sailorsmarketplace.dbmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "smarket")
public class UsersEntity {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Byte enabled;
    private String telephone;
    private UsersProfilesEntity usersProfilesByUserId;
    private String salt;
    private Collection<EventsEntity> eventsByUserId;
    private Collection<OrganizationsEntity> organizationsByUserId;
//    @ManyToMany(mappedBy = "")
    private Collection<EventsEntity> events;
    private Collection<TelegramConnectionsEntity> telegramConnectionsByUserId;

    public UsersEntity() {}

    @Id
    @GenericGenerator(name = "user_gen", strategy = "increment")
    @GeneratedValue(generator = "user_gen")
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 25)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 12)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @OneToOne(mappedBy = "usersByUserId")
    public UsersProfilesEntity getUsersProfilesByUserId() {
        return usersProfilesByUserId;
    }

    public void setUsersProfilesByUserId(UsersProfilesEntity usersProfilesByUserId) {
        this.usersProfilesByUserId = usersProfilesByUserId;
    }

    @Basic
    @Column(name = "salt", nullable = false, length = 45)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @OneToMany(mappedBy = "usersByChargeUserId")
    public Collection<EventsEntity> getEventsByUserId() {
        return eventsByUserId;
    }

    public void setEventsByUserId(Collection<EventsEntity> eventsByUserId) {
        this.eventsByUserId = eventsByUserId;
    }

    @OneToMany(mappedBy = "usersByOwnerId")
    public Collection<OrganizationsEntity> getOrganizationsByUserId() {
        return organizationsByUserId;
    }

    public void setOrganizationsByUserId(Collection<OrganizationsEntity> organizationsByUserId) {
        this.organizationsByUserId = organizationsByUserId;
    }

//    @OneToMany(mappedBy = "usersByUserId")
//    public Collection<ParticipationsEntity> getParticipationsByUserId() {
//        return participationsByUserId;
//    }
//
//    public void setParticipationsByUserId(Collection<ParticipationsEntity> participationsByUserId) {
//        this.participationsByUserId = participationsByUserId;
//    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<TelegramConnectionsEntity> getTelegramConnectionsByUserId() {
        return telegramConnectionsByUserId;
    }

    public void setTelegramConnectionsByUserId(Collection<TelegramConnectionsEntity> telegramConnectionsByUserId) {
        this.telegramConnectionsByUserId = telegramConnectionsByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId.equals(that.userId) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getEnabled(), that.getEnabled()) &&
                Objects.equals(getTelephone(), that.getTelephone()) &&
                Objects.equals(getSalt(), that.getSalt());
    }

    @Override
    public String toString() {
        if (userId != null && username != null && email != null && telephone != null && password != null && salt != null && enabled != null) {
            return userId.toString() + " " + username + " " + email + " " + telephone + " " + password + " " + salt + " " + enabled.toString();
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, enabled, telephone);
    }
}
