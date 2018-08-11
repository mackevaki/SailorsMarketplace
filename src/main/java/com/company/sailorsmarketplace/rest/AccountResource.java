package com.company.sailorsmarketplace.rest;


import com.company.sailorsmarketplace.model.Account;
import com.company.sailorsmarketplace.services.AccountData;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

@Path("/account")
public class AccountResource {
    @Context
    UriInfo uriInfo;
//    @Context
//    Request request;
    private long id;
    public AccountResource(UriInfo uriInfo, long id) {
        this.uriInfo = uriInfo;
//        this.request = request;
        this.id = id;
    }

    //Application integration
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account getAccount() {
        Account account = AccountData.getAccount(id);
        if(account==null)
            throw new RuntimeException("Get: Account with " + id +  " not found");
        return account;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Account getAcountHTML() {
        Account account = AccountData.getAccount(id);
        if(account==null)
            throw new RuntimeException("Get: Account with " + id +  " not found");
        return account;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putAccount(JAXBElement<Account> account) {
        Account c = account.getValue();
        return putAndGetResponse(c);
    }

    @DELETE
    public void deleteAccount() {
        Boolean c = AccountData.deleteAccountById(id);
        if(!c)
            throw new RuntimeException("Delete: Account with " + id +  " not found");
    }

    private Response putAndGetResponse(Account account) {
        Response res;
        if(AccountData.findAccountById(account.getAccountId()) != null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        AccountData.createAccount(account);
        return res;
    }


//     This method is called if JSON is requested
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Account getJSON() {
        return getAccount();
    }

    // This can be used to test the integration with the browser
    @GET
    @Produces({ MediaType.TEXT_XML })
    public Account getHTML() {
        return getAcountHTML();
    }

}
