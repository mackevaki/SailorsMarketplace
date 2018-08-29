package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organizations", schema = "smarket")
public class OrganizationsEntity {
    private Integer orgId;
    private String name;
    private UsersEntity usersByOwnerId;

    @Id
    @Column(name = "org_id", nullable = false)
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
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
        OrganizationsEntity that = (OrganizationsEntity) o;
        return Objects.equals(orgId, that.orgId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, name);
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false)
    public UsersEntity getUsersByOwnerId() {
        return usersByOwnerId;
    }

    public void setUsersByOwnerId(UsersEntity usersByOwnerId) {
        this.usersByOwnerId = usersByOwnerId;
    }
}
