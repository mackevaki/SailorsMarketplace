package com.company.sailorsmarketplace.dto;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder(toBuilder = true)
@ToString
public class AllVerificationParams {
    public final Date date;
    public final SourceSystem sourceSystem;
    public final String targetId;
    public final String targetUserId;
    public final String code;
}
