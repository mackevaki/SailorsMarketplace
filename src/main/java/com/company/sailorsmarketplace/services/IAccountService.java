package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dto.UserProfileDto;

public interface IAccountService {
//    User findAccountById(long id);
//    boolean deleteAccountById(long id);
//    User createAccount(User account);
//    User getAccount(long  id);
//    List<User> getAccountsList();
//    User updateAccount(User account);
    UserProfileDto saveUser(UserProfileDto user);
}
