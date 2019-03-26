package com.aashrai.db.dao;

import com.aashrai.api.Account;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

@AllArgsConstructor
public class AccountDao {

    private final MongoCollection accounts;

    private Account getAccount(ObjectId accountId) {
        return accounts.findOne(accountId).as(Account.class);
    }

    public Account getAccount(String accountId) {
        return getAccount(new ObjectId(accountId));
    }
}
