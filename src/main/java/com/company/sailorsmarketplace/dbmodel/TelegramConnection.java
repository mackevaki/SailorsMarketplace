package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "telegram_connections", schema = "smarket")
@IdClass(TelegramConnectionPK.class)
public class TelegramConnection {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "telegram_id", nullable = false)
    private Integer telegramId;

    @Basic
    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false,
                foreignKey = @ForeignKey(name = "telegram_connections_users_user_id_fk"))
    private User user;

    public TelegramConnection() {}

    public TelegramConnection(Long userId, Integer telegramId, Boolean verified, User user) {
        this.userId = userId;
        this.telegramId = telegramId;
        this.verified = verified;
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
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
        TelegramConnection that = (TelegramConnection) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(telegramId, that.telegramId) &&
                Objects.equals(verified, that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, telegramId, verified);
    }

}
