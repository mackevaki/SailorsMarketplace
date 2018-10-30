package com.company.sailorsmarketplace.dto;

import lombok.Builder;
import lombok.ToString;

import java.sql.Date;

@Builder()
@ToString
public class VerificationParams {
    public final Date date;
    public final Enum<SourceSystem> sourceSystem;
    public final String targetId;
    public final String targetUserId;


//    public VerificationParams() {
//        date = builder().date;
//        sourceSystem = builder().sourceSystem;
//        targetId = builder().targetId;
//        targetUserId = builder().targetUserId;
//    }
}
