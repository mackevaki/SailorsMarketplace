package com.company.sailorsmarketplace.dto;

import com.company.sailorsmarketplace.dbmodel.Authority;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AllUserParamsDto {
    public final Long id;
    public final String username;
    public final String email;
    public final String password;
    public final String telephone;
    public final String salt;
    public final Boolean enabled;
    public final List<Authority> authorities;

    private AllUserParamsDto(
            @NotNull Builder builder, List<Authority> authorities) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.telephone = builder.telephone;
        this.salt = builder.salt;
        this.enabled = builder.enabled;
        this.authorities = authorities;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String telephone;
        private String salt;
        private Boolean enabled;
        private List<Authority> authorities;

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
            this.authorities = authorities;
            return this;
        }

        public AllUserParamsDto build() {
            return new AllUserParamsDto(this, authorities);
        }
    }
}
