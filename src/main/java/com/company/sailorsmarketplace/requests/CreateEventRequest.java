package com.company.sailorsmarketplace.requests;

import com.company.sailorsmarketplace.dbmodel.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class CreateEventRequest {
    public final String name;
    public final String description;
    public final Date dateStart;
    public final Date dateEnd;
    public final byte[] place;
    public final Long userByChargeUserId;

    @JsonCreator
    public CreateEventRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("dateStart") Date dateStart,
            @JsonProperty("dateEnd") Date dateEnd,
            @JsonProperty("place") byte[] place,
            @JsonProperty("chargeUserId") Long userByChargeUserId) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.place = place;
        this.userByChargeUserId = userByChargeUserId;
    }

}
