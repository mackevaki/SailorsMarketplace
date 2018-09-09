package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserDAO;
import com.company.sailorsmarketplace.services.IUserService;
import com.company.sailorsmarketplace.services.UserService;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        //bind(MessageService.class).to(EmailService.class);

        //bind MessageService to Facebook Message implementation
        bind(IUserService.class).to(UserService.class);
        bind(Database.class).to(UserDAO.class);
    }
}