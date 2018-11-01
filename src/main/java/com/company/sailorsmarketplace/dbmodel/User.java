package com.company.sailorsmarketplace.dbmodel;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "smarket")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Basic
    @Column(name = "username", nullable = false, length = 25)
    @Size(max = 25)
    private String username;

    @Basic
    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Basic
    @Size(max = 12)
    @Column(name = "telephone", nullable = true, length = 12)
    private String telephone;

    @Basic
    @Column(name = "salt", nullable = true, length = 45)
    private String salt;

    @Basic
    @Column(name = "enabled", nullable = true)
    private Boolean enabled;

    @Basic
    @Column(name = "token", nullable = true, length = 60)
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    private UserProfileInfo userProfileInfo;

//    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
//    private List<Organization> organizations = new ArrayList<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Event> events = new ArrayList<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private List<TelegramConnection> telegramConnections = new ArrayList<>();

    @ElementCollection(targetClass = Authority.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = Authority.TABLE, joinColumns =
                            @JoinColumn(name = Authority.COLUMN_USERID, referencedColumnName = "user_id"))
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

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
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

    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public UserProfileInfo getUserProfileInfo() {
        return userProfileInfo;
    }

    public void setUserProfileInfo(UserProfileInfo userProfileInfo) {
        this.userProfileInfo = userProfileInfo;
    }

    public List<Event> getEvents() {
        return events;
    }

    public User addEvent(Event event) {
        this.events.add(event);
        return this;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

//    public List<Organization> getOrganizations() {
//        return organizations;
//    }
//
//    public void setOrganizations(List<Organization> organizations) {
//        this.organizations = organizations;
//    }

//    public List<TelegramConnection> getTelegramConnections() {
//        return telegramConnections;
//    }
//
//    public void setTelegramConnections(List<TelegramConnection> telegramConnectionsByUserId) {
//        this.telegramConnections = telegramConnectionsByUserId;
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
