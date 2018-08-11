package com.company.sailorsmarketplace.rest;
import com.company.sailorsmarketplace.model.Account;
import com.company.sailorsmarketplace.services.AccountData;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountsResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllAccounts() {
        List<Account> accounts = AccountData.getAccountsList();
        if (accounts != null) {
            GenericEntity<List<Account>> entity = new GenericEntity<List<Account>>(accounts){};
            return Response.ok(entity).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAccountById(@PathParam("id") long id) {
        Account account = AccountData.getAccount(id);
        if(account != null) {
            return Response.ok(account).build();
        }

        return Response.status(404).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = AccountData.getData().size();
        return String.valueOf(count);
    }


    @DELETE
    @Path("/{id}")
    public Response removeAccount(@PathParam("id") long id) {
        if (AccountData.deleteAccountById(id)) {
            return Response.ok("removed").build();
        } else {
            return Response.status(404).entity(String.format("Account with id %d does not exist\n", id)).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {
        System.out.println("POST");
        Account creAccount = AccountData.createAccount(account);
        if (creAccount != null) {
            return Response.ok(creAccount).build();
        } else {
            return Response.status(500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(Account account) {
        Account updCustomer = AccountData.updateAccount(account);
        if (updCustomer != null) {
            return Response.ok(updCustomer).build();
        } else {
            return Response.status(500).build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newAccount(@FormParam("firstname") String firstname,
                        @FormParam("lastname") String lastname,
                        @FormParam("email") String email,
                        @Context HttpServletResponse servletResponse) throws IOException {
        Account account = new Account();
        account.setFirstName(firstname);
        account.setLastName(lastname);
        account.setEmail(email);
        AccountData.createAccount(account);

        servletResponse.sendRedirect("/accountsinfo.html");
    }
}