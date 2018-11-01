package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserProfileInfoRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

import javax.inject.Inject;

public class UserProfileInfoService {

    private UserRepository userRepo;

    @Inject
    public UserProfileInfoService(final UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserProfileInfo createUserProfileInfoForNewUser(Long userId) {
        UserProfileInfo userProfileInfo = new UserProfileInfo(userId);
        User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserProfileInfoRepository dao = new UserProfileInfoRepository();

        userProfileInfo.setUser(user);
        dao.save(userProfileInfo);
        user.setUserProfileInfo(userProfileInfo);
        userRepo.update(user);

        return userProfileInfo;
    }

    public String showUserProfileInfo(Long userId) throws UserNotFoundException {
        User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return user.getUserProfileInfo().toString();
    }

    public void updateUserProfileInfo(UserProfileInfoParams params, Long userId) throws UserNotFoundException {
        User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        UserProfileInfoRepository dao = new UserProfileInfoRepository();
        UserProfileInfo userProfileInfo = dao.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        userProfileInfo.setFirstname(params.firstname);
        userProfileInfo.setLastname(params.lastname);
        userProfileInfo.setCity(params.city);
        userProfileInfo.setBirthdate(params.birthdate);
        userProfileInfo.setGender(params.gender);
        userProfileInfo.setAvatar(params.avatar);
        userProfileInfo.setOrganization(params.organization);

        user.setUserProfileInfo(userProfileInfo);
        userProfileInfo.setUser(user);
        dao.update(userProfileInfo);

        userRepo.update(user);
    }

    public UserProfileInfo getUserProfileInfoById(Long userId) throws UserNotFoundException {
        UserProfileInfoRepository dao = new UserProfileInfoRepository();

        return dao.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

}
