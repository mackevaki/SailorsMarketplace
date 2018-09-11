package com.company.sailorsmarketplace.rest;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.CreateUserRequest;
import com.company.sailorsmarketplace.dto.UserDto;
import com.company.sailorsmarketplace.exceptions.UserExistsException;
import com.company.sailorsmarketplace.services.UserService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/accounts")
public class UsersResource {
//    @Inject
//    private IUserService userService;
    private UserService userService = new UserService();
//
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces({APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response createUserProfile(@DefaultValue("") @FormParam("email") String email,
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
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (users != null) {
            GenericEntity<List<UserDto>> entity = new GenericEntity<List<UserDto>>(users) {
            };
            return Response.ok(entity.toString()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(404).build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@PathParam("id") Long id) {
        if (userService.deleteUserById(id)) {
            return Response.ok("removed").build();
        } else {
            return Response.status(404).entity(String.format("User with id %d does not exist\n", id)).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@Valid UserDto user) {
        UserDto updCustomer = userService.updateUser(user);
        if (updCustomer != null) {
            return Response.ok(updCustomer).build();
        } else {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/reg")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createUser(@Valid CreateUserRequest request) throws UserExistsException {
        if (userService.userExists(request.email)) {
            return Response.status(404).entity(new UserExistsException(request.email).getMessage()).build();
        }
        User res = userService.createNewUser(request);
        if (res == null) {
            return Response.status(500).entity("Can not create new user\n").build();
        }
        return Response.ok(res.getUserId()).build();
    }


//
//    @POST
//    @Produces(MediaType.TEXT_HTML)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void newAccount(@FormParam("email") String email,
//                        @FormParam("password") String password,
//                        @FormParam("email") String email,
//                        @Context HttpServletResponse servletResponse) throws IOException {
//        User account = new User(getCount()+1, email, email, password);
//        IUserService.createAccount(account);
//
//        servletResponse.sendRedirect("/accountsinfo.html");
//    }
//
//    @POST
//    @Path("/accinfo")
//    @Produces(MediaType.TEXT_HTML)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void addAccountInfo(@FormParam("firstname") String firstmame,
//                                   @FormParam("lastname") String lastname,
//                                   @FormParam("birhdate") Date birthdate,
//                                   @FormParam("sex") String sex,
//                                   @FormParam("city") String city,
//                                   @FormParam("organization") String organization,
//                                   @Context HttpServletResponse servletResponse) throws IOException {
//
//        servletResponse.sendRedirect("/accountsinfo.html");
//    }


}