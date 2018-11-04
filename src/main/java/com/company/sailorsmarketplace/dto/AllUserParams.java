package com.company.sailorsmarketplace.dto;

import com.company.sailorsmarketplace.dbmodel.Authority;
import com.google.common.collect.ImmutableSet;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class AllUserParams {
    public final Long id;
    public final String username;
    public final String email;
    public final String password;
    public final String telephone;
    public final String salt;
    public final Boolean enabled;
    public final Set<Authority> authorities;

    private AllUserParams(
            @NotNull Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.telephone = builder.telephone;
        this.salt = builder.salt;
        this.enabled = builder.enabled;
        this.authorities = builder.authorities.build();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String telephone;
        private String salt;
        private Boolean enabled;
        private ImmutableSet.Builder<Authority> authorities = ImmutableSet.builder();

        private Builder() {}

        public static Builder allUserParamsDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }
        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public Builder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder authorities(List<Authority> authorities) {
            this.authorities.addAll(authorities);
            return this;
        }

        public Builder withAuthority(Authority authority) {
            this.authorities.add(authority);
            return this;
        }

        public AllUserParams build() {
            return new AllUserParams(this);
        }
    }

    @Override
    public String toString() {
        return "AllUserParams{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salt='" + salt + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }


}
