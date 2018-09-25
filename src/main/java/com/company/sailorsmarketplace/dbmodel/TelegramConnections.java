package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "telegram_connections", schema = "smarket")
@IdClass(TelegramConnectionsEntityPK.class)
public class TelegramConnections {
    private Long userId;
    private Integer telegramId;
    private Byte verified;
    private User usersByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "telegram_id", nullable = false)
    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    @Basic
    @Column(name = "verified", nullable = false)
    public Byte getVerified() {
        return verified;
    }

    public void setVerified(Byte verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramConnections that = (TelegramConnections) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(telegramId, that.telegramId) &&
                Objects.equals(verified, that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, telegramId, verified);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public User getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(User usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
