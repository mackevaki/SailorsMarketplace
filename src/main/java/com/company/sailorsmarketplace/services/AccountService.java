package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.UsersEntity;
import com.company.sailorsmarketplace.dto.UserProfileDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
public class AccountService implements IAccountService {
    @Inject
    private Database database;

//    @Inject
//    public AccountService(Database database) {
//        this.database = database;
//
//    }

//    @Inject
//    public AccountService(Database database) {
//        this.database = database;
//        database.openConnection();
//    }

    @Override
    public UserProfileDto saveUser(UserProfileDto userDto) {

        UserProfileDto returnValue = null;

        UsersEntity userEntity = new UsersEntity();

        userEntity.setUserId(userDto.getAccountId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setTelephone(userDto.getTelephone());
        userEntity.setEnabled(userDto.isEnabled());
        userEntity.setSalt(userDto.getSalt());

        // Connect to database
        try {
            System.out.println("YT BGBHUJUU");

//            database.openConnection();

            UsersEntity storedUserEntity = database.saveUserProfile(userEntity);
            if(storedUserEntity != null && storedUserEntity.getUserId() > 0) {

                returnValue = new UserProfileDto();
                returnValue.setAccountId(storedUserEntity.getUserId());
                returnValue.setUsername(storedUserEntity.getUsername());
                returnValue.setPassword(storedUserEntity.getPassword());
                returnValue.setEmail(storedUserEntity.getEmail());
                returnValue.setTelephone(storedUserEntity.getTelephone());
                returnValue.setEnabled(storedUserEntity.getEnabled());
                returnValue.setSalt(storedUserEntity.getSalt());
            }
        }  finally {
//            database.closeConnection();
        }

        return returnValue;
    }

    @Override
    public String chh() {
        return database.ch();
    }
//
//    private static List<User> data;
//    private static long count = 5;
//
//    static {
//        data = new ArrayList<User>();
//        data.add(new User(1L, "Carl", "Marks", "entrygfggg"));
//        data.add(new User(2L, "hdfjkl", "2nd", "secondggggggggg"));
//        data.add(new User(3L, "hdsjk", "here", "sometextgggg"));
//        data.add(new User(4L, "dds", "HI", "pamparamgg"));
//    }
//
//    public static List<User> getData() {
//        return data;
//    }
//
//    @Override
//    public User findAccountById(long id) {
//        for (User account : data) {
//            if (account.getAccountId() == id) {
//                return account;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean deleteAccountById(long id) {
//        boolean result = false;
//        for (User account : data) {
//            if (account.getAccountId() == id) {
//                result = data.remove(account);
//                result = true;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public User updateAccount(User account) {
//        User oldAccount = null;
//        if ((account != null) && (account.getAccountId() != 0)) {
//            for (int i = 0; i < data.size(); i++) {
//                if (data.get(i).getAccountId() == account.getAccountId()) {
//                     oldAccount = getAccount(account.getAccountId());
//
//                    data.get(i).setUsername(account.getUsername());
//                    data.get(i).setPassword(account.getPassword());
//                    data.get(i).setEmail(account.getEmail());
//
//                }
//            }
//            return oldAccount;
//        }
//        return null;
//    }
//
//    @Override
//    public User createAccount(User account) {
//        if (account != null) {
//            count++;
//            account.setAccountId(count);
//            data.add(account);
//            return account;
//        }
//
//        return null;
//    }
//
//    @Override
//    public User getAccount(long id) {
//        User inList = findAccountById(id);
//        if (inList == null) {
//            return null;
//        }
//        User account = new User();
//        account.setAccountId(Objects.requireNonNull(inList).getAccountId());
//        account.setUsername(inList.getUsername());
//        account.setPassword(inList.getPassword());
//        account.setEmail(inList.getEmail());
//        return account;
//    }
//
//    @Override
//    public List<User> getAccountsList() {
//        return new ArrayList<>(data);
//    }
}
