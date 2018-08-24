package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "user_profiles")
public class AccountsInfoEntity implements Serializable {
    public AccountsInfoEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Column(name = "firstname")
    @NotEmpty(message = "Please provide your first name")
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty(message = "Please provide your last name")
    private String lastname;

    @Column(name = "city")
    private String city;

    @Column(name = "gender")
    private String gender;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }



}
