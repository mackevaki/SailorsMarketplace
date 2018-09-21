package com.company.sailorsmarketplace.dbmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "smarket")
public class User {
    @Id
    @GenericGenerator(name = "user_gen", strategy = "increment")
    @GeneratedValue(generator = "user_gen")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "username", nullable = false, length = 25)
    private String username;

    @Basic
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Basic
    @Column(name = "telephone", nullable = true, length = 12)
    private String telephone;

    @Basic
    @Column(name = "salt", nullable = false, length = 45)
    private String salt;

    @Basic
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Basic
    @Column(name = "token", nullable = false, length = 60)
    private String token;

//    @OneToOne(mappedBy = "usersByUserId")
//    private UsersProfilesEntity usersProfilesByUserId;


//    private Collection<EventsEntity> eventsByUserId;
//    @OneToMany(mappedBy = "usersByOwnerId")
//    private Collection<OrganizationsEntity> organizationsByUserId;
//    @ManyToMany(mappedBy = "usersEntities")
//    private Collection<EventsEntity> events;
//    @OneToMany(mappedBy = "usersByUserId")
//    private Collection<TelegramConnectionsEntity> telegramConnectionsByUserId;


    @ElementCollection(targetClass = Authority.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = Authority.TABLE, joinColumns = @JoinColumn(name = Authority.COLUMN_USERID, referencedColumnName = "user_id"))
    @Column(name = Authority.COLUMN_AUTHORITY)
    private List<Authority> authorities;

    public User() {}

    public User(String username, String password, String email, String telephone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
    }

    public User(String username, String password, String email, String telephone, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;

        this.authorities = new ArrayList<>();
        this.authorities.add(authority);

    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

//    public UsersProfilesEntity getUsersProfilesByUserId() {
//        return usersProfilesByUserId;
//    }
//
//    public void setUsersProfilesByUserId(UsersProfilesEntity usersProfilesByUserId) {
//        this.usersProfilesByUserId = usersProfilesByUserId;
//    }

//
//    @OneToMany(mappedBy = "usersByChargeUserId")
//    public Collection<EventsEntity> getEventsByUserId() {
//        return eventsByUserId;
//    }

//    public void setEventsByUserId(Collection<EventsEntity> eventsByUserId) {
//        this.eventsByUserId = eventsByUserId;
//    }
//
//    public Collection<OrganizationsEntity> getOrganizationsByUserId() {
//        return organizationsByUserId;
//    }
//
//    public void setOrganizationsByUserId(Collection<OrganizationsEntity> organizationsByUserId) {
//        this.organizationsByUserId = organizationsByUserId;
//    }

//    @OneToMany(mappedBy = "usersByUserId")
//    public Collection<ParticipationsEntity> getParticipationsByUserId() {
//        return participationsByUserId;
//    }
//
//    public void setParticipationsByUserId(Collection<ParticipationsEntity> participationsByUserId) {
//        this.participationsByUserId = participationsByUserId;
//    }

//    public Collection<TelegramConnectionsEntity> getTelegramConnectionsByUserId() {
//        return telegramConnectionsByUserId;
//    }
//
//    public void setTelegramConnectionsByUserId(Collection<TelegramConnectionsEntity> telegramConnectionsByUserId) {
//        this.telegramConnectionsByUserId = telegramConnectionsByUserId;
//    }

//    public Collection<Authorityy> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Collection<Authorityy> authorities) {
//        this.authorities = authorities;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
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
