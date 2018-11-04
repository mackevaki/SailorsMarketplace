package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "users_profiles")
public class UserProfileInfo {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "firstname", length = 45)
    private String firstname;

    @Basic
    @Column(name = "lastname", length = 45)
    private String lastname;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    private Date birthdate;

    @Basic
    @Column(name = "gender")
    private Enum gender;

    @Basic
    @Column(name = "city", length = 45)
    private String city;

    @Basic
    @Column(name = "organization", length = 45)
    private String organization;

    @Basic
    @Column(name = "avatar")
    private byte[] avatar;

    @Basic
    @Column(name = "telegram_id")
    private Integer telegramId;

    @OneToOne(mappedBy = "userProfileInfo")
    private User user;

    public UserProfileInfo() {}

    public UserProfileInfo(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public UserProfileInfo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserProfileInfo setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserProfileInfo setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public UserProfileInfo setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public Enum getGender() {
        return gender;
    }

    public UserProfileInfo setGender(Enum gender) {
        this.gender = gender;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserProfileInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public UserProfileInfo setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public UserProfileInfo setAvatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public UserProfileInfo setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserProfileInfo setUser(User usersByUserId) {
        this.user = usersByUserId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileInfo that = (UserProfileInfo) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(city, that.city) &&
                Objects.equals(organization, that.organization) &&
                Arrays.equals(avatar, that.avatar) &&
                Objects.equals(telegramId, that.telegramId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, firstname, lastname, birthdate, gender, city, organization, telegramId);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

    @Override
    public String toString() {
        return "User Profile:\n" +
                "user id: " + userId + "\n" +
                "firstname: " + firstname + "\n" +
                "lastname: " + lastname + "\n" +
                "birthdate: " + birthdate + "\n" +
                "gender: " + gender + "\n" +
                "city: " + city + "\n" +
                "organization: " + organization + "\n" +
                "avatar: " + Arrays.toString(avatar) + "\n" +
                "telegram id: " + telegramId + "\n";
    }

}
