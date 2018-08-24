package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "smarket")
public class UsersEntity {
    private long userId;
    private String username;
    private String email;
    private String password;
    private Byte enabled;
    private String telephone;
    private UsersProfilesEntity usersProfilesByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
    @Column(name = "enabled", nullable = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, username, email, password, enabled, telephone);
    }

    @OneToOne(mappedBy = "usersByUserId")
    public UsersProfilesEntity getUsersProfilesByUserId() {
        return usersProfilesByUserId;
    }

    public void setUsersProfilesByUserId(UsersProfilesEntity usersProfilesByUserId) {
        this.usersProfilesByUserId = usersProfilesByUserId;
    }

    private String salt;

    @Basic
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
