package com.company.sailorsmarketplace.dto;

public class AllUserParamsDto {
    public final Long id;
    public final String username;
    public final String email;
    public final String password;
    public final String telephone;
    public final String salt;
    public final byte enabled;

    private AllUserParamsDto(
            Long id,
            String username, String email, String password, String telephone, String salt,
            byte enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.salt = salt;
        this.enabled = enabled;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String telephone;
        private String salt;
        private byte enabled;

        private Builder() {}

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

        public Builder enabled(byte enabled) {
            this.enabled = enabled;
            return this;
        }

        public AllUserParamsDto build() {
            return new AllUserParamsDto(id, username, email, password, telephone, salt, enabled);
        }
    }
}
