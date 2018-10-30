package com.company.sailorsmarketplace.dto;

import lombok.Builder;
import lombok.ToString;

import java.sql.Date;

@Builder(toBuilder = true)
@ToString
public class AllVerificationParams {
    public final Date date;
    public final Enum<SourceSystem> sourceSystem;
    public final String targetId;
    public final String targetUserId;
    public final String code;
}
