package com.company.sailorsmarketplace.dto;

import java.util.Date;
import java.util.Objects;

public class UserProfile {
    private long accountId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String city;
    private String organization;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserProfile account = (UserProfile) o;
        return Objects.equals(getAccountId(), account.getAccountId()) &&
                getFirstName().compareTo(account.getFirstName()) == 0 &&
                Objects.equals(getLastName(), account.getLastName());
    }
}
