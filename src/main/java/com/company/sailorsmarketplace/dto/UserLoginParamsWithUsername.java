package com.company.sailorsmarketplace.dto;

public class UserLoginParamsWithUsername {
    public final String username;
    public final String password;

    private UserLoginParamsWithUsername(String username, String password){
        this.username = username;
        this.password = password;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;

        private Builder() {}

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserLoginParamsWithUsername build() {
            return new UserLoginParamsWithUsername(username, password);
        }
    }
}
