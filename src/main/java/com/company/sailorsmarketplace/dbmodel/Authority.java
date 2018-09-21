package com.company.sailorsmarketplace.dbmodel;

import com.company.sailorsmarketplace.security.AuthorityName;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "SMARKET", name = "AUTHORITY")
public class Authority {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

//    @ManyToMany(mappedBy = "authorities")//(cascade = {CascadeType.PERSIST, CascadeType.MERGE})//(mappedBy = "authorities", fetch = FetchType.LAZY)
//    private Collection<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

//    public Collection<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Collection<User> users) {
//        this.users = users;
//    }
}