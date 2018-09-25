package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

public interface IUserProfileInfoService {
    UserProfileInfo createUserProfileInfoForNewUser(Long userId);
    String showUserProfileInfo(Long userId);
    void updateUserProfileInfo(UserProfileInfoParams params, Long userId);
    UserProfileInfo getUserProfileInfoById(Long id) throws UserNotFoundException;
}
