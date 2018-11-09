package com.company.sailorsmarketplace.dto;

public class CreateUpdateUserParams {
    public final String username;
    public final String password;
    public final String email;
    public final String telephone;

    private CreateUpdateUserParams(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.telephone = builder.telephone;
    }

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private String telephone;

        private Builder() {
        }

        public static Builder createUpdateUserParams() {
            return new Builder();
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public CreateUpdateUserParams build() {
            return new CreateUpdateUserParams(this);
        }
    }

    @Override
    public String toString() {
        return "CreateUpdateUserParams{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", telephone='" + telephone + '\'' +
            '}';
    }
}
