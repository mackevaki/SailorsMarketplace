package com.company.sailorsmarketplace.dto;

import com.company.sailorsmarketplace.validators.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDto implements Serializable {
    @Positive(message = "Id must have positive value")
    private Long userId;
    @NotEmpty
    private String username;
    @ValidPassword
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String telephone;

    private Byte enabled;
    private String salt;

    public UserDto() {}

    public UserDto(String username, String password, String email, String telephone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}