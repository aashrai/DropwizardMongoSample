package com.aashrai.api;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Inventory {

    @MongoObjectId
    private String id;
    private String name;
    private String stock;
}
