package com.company.sailorsmarketplace.dbmodel;

import com.company.sailorsmarketplace.dto.SourceSystem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "verification_codes", schema = "smarket")
public class VerificationCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Enumerated(javax.persistence.EnumType.STRING)
    @Column(name = "source_system", nullable = false)
    private SourceSystem sourceSystem;

    @Basic
    @Column(name = "verification_code", nullable = false, length = 8)
    private String code;

    @Basic ////
    @Column(name = "valid_till", nullable = false)
    private String validTill;

    @Basic
    @Column(name = "target_id", nullable = false, length = 45)
    private String targetId;

    @Basic
    @Column(name = "target_user_id", nullable = false, length = 45)
    private Long targetUserId;


    public VerificationCode(
            SourceSystem system,
            String validTill,
            String targetId,
            Long targetUserId) {
        this.sourceSystem = system;
        this.validTill = validTill;
        this.targetId = targetId;
        this.targetUserId = targetUserId;
    }

    public VerificationCode() {
    }

    public SourceSystem getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SourceSystem sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String verificationCode) {
        this.code = verificationCode;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCode that = (VerificationCode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sourceSystem, that.sourceSystem) &&
                Objects.equals(code, that.code) &&
                Objects.equals(validTill, that.validTill) &&
                Objects.equals(targetId, that.targetId) &&
                Objects.equals(targetUserId, that.targetUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceSystem, code, validTill, targetId, targetUserId);
    }
}
