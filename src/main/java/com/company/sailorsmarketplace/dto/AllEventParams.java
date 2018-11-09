package com.company.sailorsmarketplace.dto;

import com.company.sailorsmarketplace.dbmodel.User;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AllEventParams {
    public final Long eventId;
    public final String name;
    public final String description;
    public final Date dateStart;
    public final Date dateEnd;
    public final byte[] place;
    public final User userByChargeUserId;
    public final List<User> users;

    private AllEventParams(@NotNull Builder builder) {
        this.eventId = builder.eventId;
        this.name = builder.name;
        this.description = builder.description;
        this.dateStart = builder.dateStart;
        this.dateEnd = builder.dateEnd;
        this.place = builder.place;
        this.userByChargeUserId = builder.userByChargeUserId;
        this.users = builder.users;
    }

    public static class Builder {
        private Long eventId;
        private String name;
        private String description;
        private Date dateStart;
        private Date dateEnd;
        private byte[] place;
        private User userByChargeUserId;
        private List<User> users;

        private Builder() {}

        public static Builder allEventParamsDto() {
            return new Builder();
        }

        public Builder eventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder place(byte[] place) {
            this.place = place;
            return this;
        }

        public Builder dateStart(Date dateStart) {
            this.dateStart = dateStart;
            return this;
        }

        public Builder dateEnd(Date dateEnd) {
            this.dateEnd = dateEnd;
            return this;
        }

        public Builder chargeUser(User userByChargeUserId) {
            this.userByChargeUserId = userByChargeUserId;
            return this;
        }

        public Builder users(List<User> users) {
            this.users = users;
            return this;
        }

        public AllEventParams build() {
            return new AllEventParams(this);
        }
    }

    @Override
    public String toString() {
        return "AllEventParams{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", place=" + Arrays.toString(place) +
                ", userByChargeUserId=" + userByChargeUserId +
                ", users=" + users +
                '}';
    }
}