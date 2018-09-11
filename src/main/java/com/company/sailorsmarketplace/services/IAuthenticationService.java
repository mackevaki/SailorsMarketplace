package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dto.AllUserParamsDto;
import com.company.sailorsmarketplace.exceptions.AuthenticationException;
import com.company.sailorsmarketplace.exceptions.UserExistsException;

public interface IAuthenticationService {

    AllUserParamsDto authenticate(String email, String userPassword)
            throws AuthenticationException, UserExistsException;

    String issueSecureToken(AllUserParamsDto userDto) throws AuthenticationException;

    AllUserParamsDto resetSecurityCredentials(String password, AllUserParamsDto userDto);
}
