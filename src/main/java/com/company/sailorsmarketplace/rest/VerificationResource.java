package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.AllVerificationParams;
import com.company.sailorsmarketplace.dto.VerificationParams;
import com.company.sailorsmarketplace.requests.VerificationRequest;
import com.company.sailorsmarketplace.services.VerificationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Path("/verify")
public class VerificationResource {

    @Inject
    private VerificationService verificationService;

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/send")
    public Response sendCode(VerificationRequest request) {
        AllVerificationParams params = verificationService.createVerificationCode(
                VerificationParams.builder().date(request.date)
                        .sourceSystem(request.sourceSystem)
                        .targetId(request.targetId)
                        .targetUserId(request.targetUserId).build()
        );

        return Response.ok(params.toString()).build();
    }
}
