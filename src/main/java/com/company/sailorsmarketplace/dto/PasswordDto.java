package com.company.sailorsmarketplace.dto;


import com.company.sailorsmarketplace.validators.ValidPassword;

public class PasswordDto {

    private String oldPassword;

    @ValidPassword
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}