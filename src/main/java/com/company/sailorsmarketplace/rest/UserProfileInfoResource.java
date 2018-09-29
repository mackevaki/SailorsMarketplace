package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.requests.UpdateUserProfileInfoRequest;
import com.company.sailorsmarketplace.services.IUserProfileInfoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.company.sailorsmarketplace.dto.UserProfileInfoParams.Builder.userProfileInfoParams;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/profile_info")
public class UserProfileInfoResource {

    @Inject
    IUserProfileInfoService userProfileInfoService;

    @PUT
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/update")
    public Response updateUserProfileInfo(UpdateUserProfileInfoRequest request) throws UserNotFoundException {

        UserProfileInfoParams profileInfoParams = userProfileInfoParams()
                .firstname(request.firstname)
                .lastname(request.lastname)
                .city(request.city)
                .birthdate(request.birthdate)
                .gender(request.gender)
                .avatar(request.avatar)
                .organization(request.organization)
                .build();

        userProfileInfoService.updateUserProfileInfo(profileInfoParams, request.userId);

        return Response.ok().status(Response.Status.OK).entity(request.userId).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{id}")
    public Response showUserProfileInfo(@PathParam("id") Long id) throws UserNotFoundException {

        String info = userProfileInfoService.showUserProfileInfo(id);

        return Response.ok().entity(info).build();
    }

}