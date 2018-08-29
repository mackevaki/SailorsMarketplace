package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.services.IAccountService;
import com.company.sailorsmarketplace.services.AccountService;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        //bind(MessageService.class).to(EmailService.class);

        //bind MessageService to Facebook Message implementation
        bind(IAccountService.class).to(AccountService.class);
        bind(Database.class).to(UserDAO.class);
    }
}