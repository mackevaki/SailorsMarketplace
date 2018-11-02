package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id", nullable = false)
    private Integer orgId;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false,
                foreignKey = @ForeignKey(name = "organizations_owner_id_user_id_fk"))
    private User owner;

    public Organization() {}

    public Organization(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(orgId, that.orgId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, name);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User usersByOwnerId) {
        this.owner = usersByOwnerId;
    }
}
