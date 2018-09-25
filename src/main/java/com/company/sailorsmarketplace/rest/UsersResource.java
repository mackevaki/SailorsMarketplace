package com.company.sailorsmarketplace.rest;

import com.company.sailorsmarketplace.dbmodel.Authority;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.services.IUserProfileInfoService;
import com.company.sailorsmarketplace.services.IUserService;
import com.company.sailorsmarketplace.utils.Secured;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.company.sailorsmarketplace.dto.CreateUpdateUserParams.Builder.createUpdateUserParams;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/accounts")
public class UsersResource {
    @Inject
    IUserService userService;

    @Inject
    IUserProfileInfoService userProfileInfoService;
//
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces({APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response createUserProfile(@DefaultValue("") @FormParam("username") String username,
//                                      @DefaultValue("") @FormParam("email") String email,
//                                      @DefaultValue("") @FormParam("password") String password,
//                                      @DefaultValue("") @FormParam("telephone") String telephone,
//                                      @Context HttpServletResponse servletResponse) {

//               servletResponse.sendRedirect("/accountsinfo.html");
//
//        // And when we are done, we can return user profile back
//        return Response.ok(returnValue).build();//userProfile;
//    }


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
            return Response.status(404).build();
        }
    }

    @GET
    @Secured
    @Produces(APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return Response.ok(user.getUserId()).build();
        } else {
            return Response.status(404).build();
        }
    }


    @DELETE
    @Secured
    @Path("/{id}")
    public Response removeUser(@PathParam("id") Long id) {
        if (userService.deleteUser(id)) {
            return Response.ok("removed").build();
        } else {
            return Response.status(404).entity(String.format("User with id %d does not exist\n", id)).build();
        }
    }

    @PUT
    @Secured
    @Consumes(APPLICATION_JSON)
    public Response updateUser(@Valid UpdateUserRequest request) {

        final User updUser = userService.updateUser(
                createUpdateUserParams()
                        .username(request.username)
                        .password(request.password)
                        .email(request.email)
                        .telephone(request.telephone)
                        .build(), request.userId
        );
        if (updUser != null) {
            return Response.ok(updUser.getUserId()).build();
        } else {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/reg")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createUser(@Valid CreateUserRequest request) throws UserExistsException {
//        if (userService.userExists(request.email)) {
//            return Response.status(400).entity(new UserExistsException(request.email)).build();
//        }

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