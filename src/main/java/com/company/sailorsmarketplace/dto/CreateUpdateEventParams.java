package com.company.sailorsmarketplace.dto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

public class CreateUpdateEventParams {
    public final String name;
    public final String description;
    public final Date dateStart;
    public final Date dateEnd;
    public final byte[] place;
    public final Long chargeUserId;

    private CreateUpdateEventParams(@NotNull Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.dateStart = builder.dateStart;
        this.dateEnd = builder.dateEnd;
        this.place = builder.place;
        this.chargeUserId = builder.chargeUsrId;
    }

    public static class Builder {
        private String name;
        private String description;
        private Date dateStart;
        private Date dateEnd;
        private byte[] place;
        private Long chargeUsrId;

        private Builder() {}

        public static Builder createUpdateEventParams() {
            return new Builder();
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

        public Builder chargeUser(Long chargeUserId) {
            this.chargeUsrId = chargeUserId;
            return this;
        }

        public CreateUpdateEventParams build() {
            return new CreateUpdateEventParams(this);
        }
    }

    @Override
    public String toString() {
        return "CreateUpdateEventParams{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", dateStart=" + dateStart +
            ", dateEnd=" + dateEnd +
            ", place=" + Arrays.toString(place) +
            ", chargeUserId=" + chargeUserId +
            '}';
    }
}
