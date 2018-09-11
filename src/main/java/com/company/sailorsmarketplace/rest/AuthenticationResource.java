package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dto.AllUserParamsDto;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.services.AuthenticationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

@Path("/authentication")
public class AuthenticationResource {
//    private AuthenticationService authenticationService = new AuthenticationService();

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticationDetails userLogin(AuthenticationRequest loginCredentials) {
        AuthenticationDetails returnValue = new AuthenticationDetails();

        AllUserParamsDto userProfile = null;

        // Perform User Authentication
        try {
            userProfile = authenticationService.authenticate(
                    loginCredentials.email,
                    loginCredentials.password);
        } catch (AuthenticationException | UserNotFoundException ex) {
            Logger.getLogger(AuthenticationResource.class.getName()).log(Level.SEVERE, null, ex);
            return returnValue;
        }

        // Reset Access Token
        authenticationService.resetSecurityCredentials(loginCredentials.password, userProfile);
        // Issue a new Access token
        String secureUserToken = authenticationService.issueSecureToken(userProfile);

        returnValue.setToken(secureUserToken);
        returnValue.setId(userProfile.id);

        return returnValue;
    }

//    @POST
//    public Response createAuthenticationToken(AuthenticationRequest authenticationRequest) throws AuthenticationException {
//
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        // Reload password post-security so we can generate the token
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        // Return the token
//        return Response.ok(new JwtAuthenticationResponse(token));
//    }
//
//    /**
//     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
//     */
//    private void authenticate(String email, String password) {
//        requireNonNull(email);
//        requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        } catch (DisabledException e) {
//            throw new AuthenticationException("User is disabled!", e);
//        } catch (BadCredentialsException e) {
//            throw new AuthenticationException("Bad credentials!", e);
//        }
//    }

}
