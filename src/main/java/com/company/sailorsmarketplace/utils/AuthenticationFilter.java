package com.company.sailorsmarketplace.utils;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.AuthenticationServiceException;
import com.company.sailorsmarketplace.services.AuthenticationService;
import com.company.sailorsmarketplace.services.IUserService;
import com.company.sailorsmarketplace.services.UserService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Inject
    IUserService usersService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        Long userId = Long.valueOf(requestContext.getUriInfo().getPathParameters().getFirst("id"));

        try {
            validateToken(token, userId);
        } catch (AuthenticationServiceException ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token, Long userId) throws AuthenticationServiceException {
        User userProfile = usersService.getUserById(userId);

        String completeToken = userProfile.getToken() + token;
        String securePassword = userProfile.getPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;

        byte[] encryptedAccessToken = null;
        encryptedAccessToken = AuthenticationUtil.encrypt(securePassword, accessTokenMaterial);

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
        if (!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
            throw new AuthenticationServiceException("Authorization token did not match");
        }
    }
}