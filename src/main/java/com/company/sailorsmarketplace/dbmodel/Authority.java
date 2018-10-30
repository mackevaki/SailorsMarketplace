package com.company.sailorsmarketplace.dbmodel;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

public enum Authority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_ANONYMOUS;

    @Cascade(CascadeType.ALL)
    public static final String TABLE = "authorities";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_AUTHORITY = "authority";
}