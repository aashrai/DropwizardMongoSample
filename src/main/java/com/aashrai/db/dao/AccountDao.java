package com.aashrai.db.dao;

import com.aashrai.api.Account;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

public class AccountDao {

    private final MongoCollection accounts;

    public AccountDao(MongoCollection accounts) {
        this.accounts = accounts;
    }

    private Account getAccount(ObjectId accountId) {
        return accounts.findOne(accountId).as(Account.class);
    }

    public Account getAccount(String accountId) {
        return getAccount(new ObjectId(accountId));
    }
}
