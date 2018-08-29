package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TelegramConnectionsEntityPK implements Serializable {
    private Long userId;
    private Integer telegramId;

    @Column(name = "user_id", nullable = false)
    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "telegram_id", nullable = false)
    @Id
    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramConnectionsEntityPK that = (TelegramConnectionsEntityPK) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(telegramId, that.telegramId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, telegramId);
    }
}
