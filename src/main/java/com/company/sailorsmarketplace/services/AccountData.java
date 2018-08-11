package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// model
public class AccountData {

    private static List<Account> data;
    private static long count = 5;

    static {
        data = new ArrayList<Account>();
        data.add(new Account(1L, "Carl", "Marks", "entry"));
        data.add(new Account(2L, "hdfjkl", "2nd", "second"));
        data.add(new Account(3L, "hdsjk", "here", "sometext"));
        data.add(new Account(4L, "dds", "HI", "pamparam"));
    }

    public static List<Account> getData() {
        return data;
    }

    public static Account findAccountById(long id) {
        for (Account account : data) {
            if (account.getAccountId() == id) {
                return account;
            }
        }
        return null;
    }

    public static boolean deleteAccountById(long id) {
        boolean result = false;
        for (Account account : data) {
            if (account.getAccountId() == id) {
                result = data.remove(account);
                result = true;
            }
        }
        return result;
    }

    public static Account updateAccount(Account account) {
        Account oldAccount = null;
        if ((account != null) && (account.getAccountId() != 0)) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getAccountId() == account.getAccountId()) {
                     oldAccount = getAccount(account.getAccountId());

                    data.get(i).setFirstName(account.getFirstName());
                    data.get(i).setLastName(account.getLastName());
                    data.get(i).setEmail(account.getEmail());

                }
            }
            return oldAccount;
        }
        return null;
    }

    public static Account createAccount(Account account) {
        if (account != null) {
            count++;
            account.setAccountId(count);
            data.add(account);
            return account;
        }

        return null;
    }

    public static Account getAccount(long id) {
        Account inList = findAccountById(id);
        if (inList == null) {
            return null;
        }
        Account account = new Account();
        account.setAccountId(Objects.requireNonNull(inList).getAccountId());
        account.setFirstName(inList.getFirstName());
        account.setLastName(inList.getLastName());
        account.setEmail(inList.getEmail());

        return account;
    }

    public static List<Account> getAccountsList() {
        List<Account> list = new ArrayList<>();
        for (Account acc : data) {
            list.add(acc);
        }
        return list;
    }
}
