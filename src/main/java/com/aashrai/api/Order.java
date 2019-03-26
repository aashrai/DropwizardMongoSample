package com.aashrai.api;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Order {

    @MongoObjectId
    private String id;
    @MongoObjectId
    private String pid;
    @MongoObjectId
    private String accountId;
}
