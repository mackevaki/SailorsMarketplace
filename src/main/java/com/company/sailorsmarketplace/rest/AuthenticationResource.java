package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.dto.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.services.AuthenticationService;
import com.company.sailorsmarketplace.utils.Secured;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class AuthenticationResource {
    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationResource(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@Valid AuthenticationRequest loginCredentials) {
        AllUserParams userProfile = authenticationService.authenticate(
                loginCredentials.email,
                loginCredentials.password);
        // Reset Access Token
        userProfile = authenticationService.resetSecurityCredentials(loginCredentials.password, userProfile);
        // Issue a new Access token
        final String secureUserToken = authenticationService.issueSecureToken(userProfile);
        final AuthenticationDetails authenticationDetails = new AuthenticationDetails(userProfile.id, secureUserToken);

        return Response.ok().status(HttpStatus.SC_OK).entity(authenticationDetails.toString()).header(HttpHeaders.AUTHORIZATION, "Bearer " + secureUserToken).build();
    }

    @POST
    @Path("/{id}/logout")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogout(@PathParam("id") Long userId) {
        final User user = authenticationService.removeSecureCredentials(userId);

        return Response.ok().status(HttpStatus.SC_OK).entity(user.getUserId()).build();
    }
}
