package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.requests.CreateUserRequest;
import com.company.sailorsmarketplace.requests.UpdateUserRequest;
import com.company.sailorsmarketplace.services.UserProfileInfoService;
import com.company.sailorsmarketplace.services.UserService;
import com.company.sailorsmarketplace.utils.Secured;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.company.sailorsmarketplace.dto.CreateUpdateUserParams.Builder.createUpdateUserParams;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
public class UsersResource {
    private final UserService userService;
    private final UserProfileInfoService userProfileInfoService;

    @Inject
    public UsersResource(final UserService userService, final UserProfileInfoService userProfileInfoService) {
        this.userService = userService;
        this.userProfileInfoService = userProfileInfoService;
    }

    @GET
    @Secured
    @Path("/all")
    @Produces({APPLICATION_JSON})
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null) {
            GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {
            };
            return Response.ok(entity.getEntity().toString()).build();
        } else {
            return Response.status(HttpStatus.SC_NOT_FOUND).build();
        }
    }

    @GET
    @Secured
    @Produces(APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {

        User user = userService.getUserById(id);

        if(user != null) {
            return Response.ok(user.toString()).build();
        } else {
            return Response.status(HttpStatus.SC_NOT_FOUND).build();
        }
    }


    @DELETE
    @Secured
    @Path("/{id}")
    public Response removeUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.ok("removed").build();
    }

    @PUT
    @Secured
    @Consumes(APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateUser(@Valid UpdateUserRequest request, @PathParam("id") Long userId) {
        final User updUser = userService.updateUser(
                createUpdateUserParams()
                        .username(request.username)
                        .password(request.password)
                        .email(request.email)
                        .telephone(request.telephone)
                        .build(), request.userId
        );

        return Response.ok(updUser.getUserId()).build();
    }

    @POST
    @Path("/reg")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createUser(@Valid CreateUserRequest request)
            throws UserExistsException, ConstraintViolationException {
        final User createdUser = userService.createNewUser(
                createUpdateUserParams()
                        .username(request.username)
                        .password(request.password)
                        .email(request.email)
                        .telephone(request.telephone)
                        .build(),
                Authority.ROLE_USER
        );

        userProfileInfoService.createUserProfileInfoForNewUser(createdUser.getUserId());

        return Response.ok(createdUser.getUserId()).build();
    }

}