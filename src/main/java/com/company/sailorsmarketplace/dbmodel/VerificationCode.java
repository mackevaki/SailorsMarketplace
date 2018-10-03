package com.company.sailorsmarketplace.dbmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "verification_codes", schema = "smarket")
public class VerificationCode implements Serializable {

    @Id
//    @GenericGenerator(name = "verif_gen", strategy = "increment")
//    @GeneratedValue(generator = "verif_gen")
    @Column(name = "source_system", nullable = false)
    private EnumType sourceSystem;

    @Basic
    @Column(name = "verification_code", nullable = false, length = 7)
    private String verificationCode;

    @Basic
    @Column(name = "valid_till", nullable = false)
    private Date validTill;

    @Basic
    @Column(name = "target_id", nullable = false, length = 45)
    private String targetId;

    @Basic
    @Column(name = "target_user_id", nullable = false, length = 45)
    private String targetUserId;

    public VerificationCode() {}

    public VerificationCode(String verificationCode, Date validTill, String targetId, String targetUserId) {
        this.verificationCode = verificationCode;
        this.validTill = validTill;
        this.targetId = targetId;
        this.targetUserId = targetUserId;
    }

    public EnumType getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(EnumType sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

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
        VerificationCode that = (VerificationCode) o;
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
