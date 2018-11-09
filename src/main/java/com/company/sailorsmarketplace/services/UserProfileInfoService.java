package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.UserProfileInfoRepository;
import com.company.sailorsmarketplace.dao.UserRepository;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.google.inject.Inject;

public class UserProfileInfoService {
    private final UserRepository userRepo;
    private final UserProfileInfoRepository userProfileInfoRepo;

    @Inject
    public UserProfileInfoService(final UserRepository userRepo, final UserProfileInfoRepository userProfileInfoRepo) {
        this.userRepo = userRepo;
        this.userProfileInfoRepo = userProfileInfoRepo;
    }

    public UserProfileInfo createUserProfileInfoForNewUser(final Long userId) {
        final UserProfileInfo userProfileInfo = new UserProfileInfo(userId);
        final User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        userProfileInfo.setUser(user);
        userProfileInfoRepo.save(userProfileInfo);
        user.setUserProfileInfo(userProfileInfo);
        userRepo.update(user);

        return userProfileInfo;
    }

    public String showUserProfileInfo(final Long userId) {
        final User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getUserProfileInfo().toString();
    }

    public UserProfileInfo updateUserProfileInfo(final UserProfileInfoParams params, final Long userId) {
        final User user = userRepo.getById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return userProfileInfoRepo.getById(userId)
                .map(profileInfo -> profileInfo.setFirstname(params.firstname))
                .map(profileInfo -> profileInfo.setLastname(params.lastname))
                .map(profileInfo -> profileInfo.setCity(params.city))
                .map(profileInfo -> profileInfo.setBirthdate(params.birthdate))
                .map(profileInfo -> profileInfo.setGender(params.gender))
                .map(profileInfo -> profileInfo.setAvatar(params.avatar))
                .map(profileInfo -> profileInfo.setOrganization(params.organization))
                .map(profileInfo -> {
                    user.setUserProfileInfo(profileInfo);
                    profileInfo.setUser(user);
                    userProfileInfoRepo.update(profileInfo);
                    userRepo.update(user);

                    return profileInfo;
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserProfileInfo getUserProfileInfoById(final Long id) {
        return userProfileInfoRepo.getById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
