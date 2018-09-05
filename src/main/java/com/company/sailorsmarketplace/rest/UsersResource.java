package com.company.sailorsmarketplace.rest;
import com.company.sailorsmarketplace.dto.UserProfileDto;
import com.company.sailorsmarketplace.model.User;
import com.company.sailorsmarketplace.services.IAccountService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URL;
import java.util.Random;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/accounts")
//@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    @Inject
    private IAccountService accountService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUserProfile(@DefaultValue("") @FormParam("username") String username,
                                      @DefaultValue("") @FormParam("email") String email,
                                      @DefaultValue("") @FormParam("password") String password,
                                      @Context HttpServletResponse servletResponse) {
        User userProfile = new User();
        userProfile.setPassword(password);
        userProfile.setUsername(username);
        userProfile.setEmail(email);
        userProfile.setTelephone("562779339");
        userProfile.setEnabled((byte) 1);
        userProfile.setAccountId(new Random().nextLong());
        userProfile.setSalt("tokggrandom");
        User returnValue = null;

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setAccountId(userProfile.getAccountId());
        userProfileDto.setUsername(userProfile.getUsername());
        userProfileDto.setPassword(userProfile.getPassword());
        userProfileDto.setEmail(userProfile.getEmail());
        userProfileDto.setTelephone(userProfile.getTelephone());
        userProfileDto.setEnabled(userProfile.isEnabled());
        userProfileDto.setSalt(userProfile.getSalt());

        System.out.println("EMAIL       !      " + email + "      ! username                     " + username + "          ! passs    " + password);
            if (accountService == null) {
                System.out.println("22222222222222222222222222222222U");

            } else {
                System.out.println("333333333333333U");

            }
//        IAccountService usersService = new AccountService();
        UserProfileDto storedUserDetails = accountService.saveUser(userProfileDto);

        if(storedUserDetails != null && !storedUserDetails.getUsername().isEmpty()) {
            returnValue = new User();
            returnValue.setAccountId(storedUserDetails.getAccountId());
            returnValue.setUsername(storedUserDetails.getUsername());
            returnValue.setPassword(storedUserDetails.getPassword());
            returnValue.setEmail(storedUserDetails.getEmail());
            returnValue.setTelephone(storedUserDetails.getTelephone());
            returnValue.setEnabled(storedUserDetails.isEnabled());
            returnValue.setSalt(storedUserDetails.getSalt());
//            returnValue.setConfirmationToken(storedUserDetails.getConfirmationToken());
        }
//                servletResponse.sendRedirect("/accountsinfo.html");

        // And when we are done, we can return user profile back
        return Response.ok(returnValue).build();//userProfile;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/registration")
    public Response register() throws IOException {
//        servletResponse.setContentType("text/html");
//        PrintWriter out = new PrintWriter(servletResponse.getOutputStream());
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine).append("\n");
//        }
//        servletResponse.sendRedirect("/signup.html");

//        Reader stringReader = new StringReader(response.toString());
//        HTMLEditorKit htmlKit = new HTMLEditorKit();
//        HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
//        HTMLEditorKit.Parser parser = new ParserDelegator();
//        parser.parse(stringReader, htmlDoc.getReader(0), true);
        return Response.ok(new URL("//signup.html")).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        return "gggg" + accountService.chh();
    }

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllAccounts() {
//        List<User> accounts = IAccountService.getAccountsList();
//        if (accounts != null) {
//            GenericEntity<List<User>> entity = new GenericEntity<List<User>>(accounts) {
//            };
//            return Response.ok(entity).build();
//        } else {
//            return Response.status(404).build();
//        }
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{id}")
//    public Response getAccountById(@PathParam("id") long id) {
//        User account = IAccountService.getAccount(id);
//        if(account != null) {
//            return Response.ok(account).build();
//        }
//
//        return Response.status(404).build();
//    }

//    @GET
//    @Path("/count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public long getCount() {
//        long count = AccountService.getData().size();
//        return Long.parseLong(String.valueOf(count));
//    }
//
//
//    @DELETE
//    @Path("/{id}")
//    public Response removeAccount(@PathParam("id") long id) {
//        if (IAccountService.deleteAccountById(id)) {
//            return Response.ok("removed").build();
//        } else {
//            return Response.status(404).entity(String.format("User with id %d does not exist\n", id)).build();
//        }
//    }
//
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createAccount(User account) {
//        System.out.println("POST");
//        User creAccount = IAccountService.createAccount(account);
//        if (creAccount != null) {
//            return Response.ok(creAccount).build();
//        } else {
//            return Response.status(500).build();
//        }
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateAccount(User account) {
//        User updCustomer = IAccountService.updateAccount(account);
//        if (updCustomer != null) {
//            return Response.ok(updCustomer).build();
//        } else {
//            return Response.status(500).build();
//        }
//    }
//
//    @POST
//    @Produces(MediaType.TEXT_HTML)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void newAccount(@FormParam("username") String username,
//                        @FormParam("password") String password,
//                        @FormParam("email") String email,
//                        @Context HttpServletResponse servletResponse) throws IOException {
//        User account = new User(getCount()+1, username, email, password);
//        IAccountService.createAccount(account);
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

//    @POST
//    @Consumes(APPLICATION_JSON)
//    @Produces(APPLICATION_JSON)
//    public Response createUser(CreateUserRequest request) {
//
//        return;
//    }
//
//    static class CreateUserRequest {
//        public final String username;
//        public final String password;
//        public final String email;
//
//        @JsonCreator
//        public CreateUserRequest(
//                @JsonProperty("username") String username,
//                @JsonProperty("password") String password,
//                @JsonProperty("email") String email) {
//            this.username = username;
//            this.password = password;
//            this.email = email;
//        }
//    }
}