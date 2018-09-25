package com.company.sailorsmarketplace.dto;

import javax.validation.constraints.NotNull;

public class PasswordDto {
    public final String oldPassword;
    public final String newPassword;

    private PasswordDto(
            @NotNull PasswordDto.Builder builder) {
        this.oldPassword = builder.oldPassword;
        this.newPassword = builder.newPassword;
    }

    public static class Builder {
        private String oldPassword;
        private String newPassword;

        private Builder() {}

        public static Builder passwordDto() {
            return new PasswordDto.Builder();
        }

        public Builder oldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }
    }


}