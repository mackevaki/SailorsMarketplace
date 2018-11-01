package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.AllEventParams;
import com.company.sailorsmarketplace.dto.CreateUpdateEventParams;
import com.company.sailorsmarketplace.requests.CreateEventRequest;
import com.company.sailorsmarketplace.services.EventService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.company.sailorsmarketplace.dto.CreateUpdateEventParams.Builder.createUpdateEventParams;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/events")
public class EventResource {

    private final EventService eventService;

    @Inject
    public EventResource(final EventService eventService) {
        this.eventService = eventService;
    }

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

    //@Secured
    @DELETE
    @Path("/{id}")
    public Response removeEvent(@PathParam("id") Long id) {
        if (eventService.deleteEvent(id)) {
            return Response.ok("removed").build();
        } else {
            return Response.status(404).entity(String.format("Event with id %d does not exist\n", id)).build();
        }
    }


}
