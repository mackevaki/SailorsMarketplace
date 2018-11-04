package com.company.sailorsmarketplace.dto;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;

@Builder()
@ToString
public class VerificationParams {
    public final Date date;
    public final SourceSystem sourceSystem;
    public final String targetId;
    public final Long targetUserId;
}
