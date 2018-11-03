package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserProfileInfoRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;

import javax.inject.Inject;

public class UserProfileInfoService {

    private final UserRepository userRepo;
    private final UserProfileInfoRepository userProfileInfoRepo;


    @Inject
    public UserProfileInfoService(final UserRepository userRepo,
                                  final UserProfileInfoRepository userProfileInfoRepo) {
        this.userRepo = userRepo;
        this.userProfileInfoRepo = userProfileInfoRepo;
    }

    public UserProfileInfo createUserProfileInfoForNewUser(Long userId) {
        UserProfileInfo userProfileInfo = new UserProfileInfo(userId);
        User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        userProfileInfo.setUser(user);
        userProfileInfoRepo.save(userProfileInfo);
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

        UserProfileInfo userProfileInfo = userProfileInfoRepo.getById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userProfileInfo.setFirstname(params.firstname);
        userProfileInfo.setLastname(params.lastname);
        userProfileInfo.setCity(params.city);
        userProfileInfo.setBirthdate(params.birthdate);
        userProfileInfo.setGender(params.gender);
        userProfileInfo.setAvatar(params.avatar);
        userProfileInfo.setOrganization(params.organization);

        user.setUserProfileInfo(userProfileInfo);
        userProfileInfo.setUser(user);
        userProfileInfoRepo.update(userProfileInfo);

        userRepo.update(user);
    }

    public UserProfileInfo getUserProfileInfoById(Long userId) throws UserNotFoundException {
        return userProfileInfoRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

}
