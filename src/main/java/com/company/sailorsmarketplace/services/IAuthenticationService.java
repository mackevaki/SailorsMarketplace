package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dto.AllUserParams;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

public interface IAuthenticationService {

    AllUserParams authenticate(String email, String userPassword) throws AuthenticationException, UserNotFoundException;

    String issueSecureToken(AllUserParams userDto) throws AuthenticationException;

    AllUserParams resetSecurityCredentials(String password, AllUserParams userDto);

    User removeSecureCredentials(Long userId);
}
