package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "users_profiles", schema = "smarket")
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

    @Basic
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User usersByUserId) {
        this.user = usersByUserId;
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
