package com.company.sailorsmarketplace.dbmodel;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "verification_codes", schema = "smarket")
public class VerificationCodesEntity implements Serializable {
    private EnumType sourceSystem;
    private String verificationCode;
    private Date validTill;
    private String targetId;
    private String targetUserId;

    @Id
    @Column(name = "source_system", nullable = false)
    public EnumType getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(EnumType sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    @Basic
    @Column(name = "verification_code", nullable = false, length = 7)
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Basic
    @Column(name = "valid_till", nullable = false)
    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    @Basic
    @Column(name = "target_id", nullable = false, length = 45)
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Basic
    @Column(name = "target_user_id", nullable = false, length = 45)
    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCodesEntity that = (VerificationCodesEntity) o;
        return Objects.equals(sourceSystem, that.sourceSystem) &&
                Objects.equals(verificationCode, that.verificationCode) &&
                Objects.equals(validTill, that.validTill) &&
                Objects.equals(targetId, that.targetId) &&
                Objects.equals(targetUserId, that.targetUserId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sourceSystem, verificationCode, validTill, targetId, targetUserId);
    }
}
