package com.company.sailorsmarketplace;

import com.company.sailorsmarketplace.dbmodel.AccountEntity;

public interface UserRepository {
    AccountEntity findByEmail(String email);
    AccountEntity findByConfirmationToken(String confirmationToken);
}
