package com.company.sailorsmarketplace.requests;

import com.company.sailorsmarketplace.dto.SourceSystem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class VerificationRequest {
    public final Date date;
    public final SourceSystem sourceSystem;
    public final String targetId;
    public final String targetUserId;


    @JsonCreator
    public VerificationRequest(
            @JsonProperty("date") Date date,
            @JsonProperty("sourceSystem") SourceSystem sourceSystem,
            @JsonProperty("targetId") String targetId,
            @JsonProperty("targetUserId") String targetUserId) {
        this.date = date;
        this.sourceSystem = sourceSystem;
        this.targetId = targetId;
        this.targetUserId = targetUserId;
    }
}
