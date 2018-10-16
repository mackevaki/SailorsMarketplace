package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.company.sailorsmarketplace.services.IEventService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static com.company.sailorsmarketplace.dto.CreateUpdateEventParams.Builder.createUpdateEventParams;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/events")
public class EventResource {

    @Inject
    IEventService eventService;

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/create")
    public Response createEvent(CreateEventRequest request) {
        CreateUpdateEventParams createEventParams = createUpdateEventParams()
                .name(request.name)
                .description(request.description)
                .place(request.place)
                .dateStart(request.dateStart)
                .dateEnd(request.dateEnd)
                .chargeUser(request.userByChargeUserId)
                .build();

        AllEventParams allEventParams = eventService.createEvent(createEventParams);

        return Response.ok(String.format("%d", allEventParams.eventId)).build();
    }
}
