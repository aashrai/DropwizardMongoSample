package com.aashrai.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @MongoObjectId
    private String _id;
    @MongoObjectId
    private String pid;
    @MongoObjectId
    private String accountId;
    private Date date;
}
