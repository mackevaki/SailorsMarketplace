package com.company.sailorsmarketplace.dao;

import com.company.sailorsmarketplace.dbmodel.AccountEntity;
import com.company.sailorsmarketplace.dbmodel.UsersEntity;

public interface Database {
//    void openConnection();
    UsersEntity saveUserProfile(UsersEntity userProfile);

    String ch();
//    void closeConnection();
}
