package com.company.sailorsmarketplace.dto;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UserProfileInfoParams {
    public final String firstname;
    public final String lastname;
    public final Date birthdate;
    public final String city;
    public final Enum gender;
    public final String organization;
    public final byte[] avatar;

    public UserProfileInfoParams(@NotNull Builder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.birthdate = builder.birthdate;
        this.gender = builder.gender;
        this.city = builder.city;
        this.organization = builder.organization;
        this.avatar = builder.avatar;
    }

    public static class Builder {
        private String firstname;
        private String lastname;
        private Date birthdate;
        private Enum gender;
        private String city;
        private String organization;
        private byte[] avatar;


        private Builder() {
        }

        public static Builder userProfileInfoParams() {
            return new Builder();
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder birthdate(Date birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public Builder gender(Enum gender) {
            this.gender = gender;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder organization(String organization) {
            this.organization = organization;
            return this;
        }

        public Builder avatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserProfileInfoParams build() {
            return new UserProfileInfoParams(this);
        }
    }
}
