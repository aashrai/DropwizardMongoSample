package com.aashrai.api;

import lombok.Data;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

@Data
public class Order {

    @MongoObjectId
    private String _id;
    @MongoObjectId
    private String pid;
    @MongoObjectId
    private String accountId;
    private Date date;
}
