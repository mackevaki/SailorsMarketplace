package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dao.UserProfileInfoDAO;
import com.company.sailorsmarketplace.dbmodel.User;
import com.company.sailorsmarketplace.dbmodel.UserProfileInfo;
import com.company.sailorsmarketplace.dto.UserProfileInfoParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

public class UserProfileInfoService implements IUserProfileInfoService {

    @Inject
    Database database;

    @Override
    public UserProfileInfo createUserProfileInfoForNewUser(Long userId) {

        UserProfileInfo userProfileInfo = new UserProfileInfo(userId);

        User user = database.getById(userId);
        userProfileInfo.setUser(user);

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        dao.save(userProfileInfo);

        user.setUserProfileInfo(userProfileInfo);
        database.update(user);

        return userProfileInfo;
    }

    @Override
    public String showUserProfileInfo(Long userId) throws UserNotFoundException {

        User user = database.getById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user.getUserProfileInfo().toString();
    }

    @Override
    public void updateUserProfileInfo(UserProfileInfoParams params, Long userId) throws UserNotFoundException {

        User user;
        if ((user = database.getById(userId)) == null) {
            throw new UserNotFoundException();
        }

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        UserProfileInfo userProfileInfo = dao.getById(userId);

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
        database.update(user);
    }

    @Override
    public UserProfileInfo getUserProfileInfoById(Long id) throws UserNotFoundException {

        UserProfileInfoDAO dao = new UserProfileInfoDAO();
        UserProfileInfo userProfileInfo = dao.getById(id);

        if (userProfileInfo == null) {
            throw new UserNotFoundException();
        }

        return userProfileInfo;
    }

}
