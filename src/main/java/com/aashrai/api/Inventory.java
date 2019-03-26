package com.aashrai.api;

import lombok.Data;
import org.jongo.marshall.jackson.oid.MongoObjectId;

@Data
public class Inventory {
    @MongoObjectId
    private String _id;
    private String name;
    private String stock;
    private Integer cost;
}
