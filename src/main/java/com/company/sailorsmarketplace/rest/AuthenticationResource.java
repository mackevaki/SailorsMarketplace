package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.requests.AuthenticationDetails;
import com.company.sailorsmarketplace.requests.AuthenticationRequest;
import com.company.sailorsmarketplace.services.IAuthenticationService;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class AuthenticationResource {
    @Inject
    IAuthenticationService authenticationService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@Valid AuthenticationRequest loginCredentials) throws AuthenticationException, UserNotFoundException{

        // Perform User Authentication
        AllUserParams userProfile = authenticationService.authenticate(
                loginCredentials.email,
                loginCredentials.password);

        // Reset Access Token
        userProfile = authenticationService.resetSecurityCredentials(loginCredentials.password, userProfile);
        // Issue a new Access token
        String secureUserToken = authenticationService.issueSecureToken(userProfile);
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(userProfile.id, secureUserToken);

        return Response.ok().status(HttpStatus.SC_OK).entity(authenticationDetails).build();
    }
}
