package com.aashrai.api;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Account {

    @MongoObjectId
    private String id;
    private String name;
    private String address;
    private String email;
}
