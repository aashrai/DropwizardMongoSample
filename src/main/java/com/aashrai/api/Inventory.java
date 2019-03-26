package com.aashrai.api;

import org.bson.types.ObjectId;

public class Inventory {

    private ObjectId _id;
    private String name;
    private String stock;

    public ObjectId get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getStock() {
        return stock;
    }
}
