package com.company.sailorsmarketplace.services;

import com.company.sailorsmarketplace.dao.Database;
import com.company.sailorsmarketplace.dbmodel.VerificationCode;
import com.company.sailorsmarketplace.dto.AllVerificationParams;
import com.company.sailorsmarketplace.dto.VerificationParams;
import com.company.sailorsmarketplace.exceptions.UserNotFoundException;
import com.company.sailorsmarketplace.utils.HibernateUtils;
import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class VerificationService {

    @Inject
    private Database database;

    public AllVerificationParams createVerificationCode(VerificationParams params) {

        VerificationCode verificationCode = new VerificationCode(
                params.sourceSystem,
                params.date.toString(),
                params.targetId,
                params.targetUserId
        );

        String code = randomNumeric(8);

        if (database.getById(Long.valueOf(params.targetUserId)) != null) {
            verificationCode.setCode(code);

            try (final Session session = HibernateUtils.getSessionFactory().openSession()) {
                final Transaction tx = session.beginTransaction();

                session.save(verificationCode);
                tx.commit();
            }
        } else {
            throw new UserNotFoundException(params.targetUserId);
        }

        return AllVerificationParams.builder()
                .sourceSystem(params.sourceSystem)
                .code(code)
                .date(params.date)
                .targetId(params.targetId)
                .targetUserId(params.targetUserId).build();
    }
}
