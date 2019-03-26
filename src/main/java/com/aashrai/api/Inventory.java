package com.aashrai.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jongo.marshall.jackson.oid.MongoObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @MongoObjectId
    private String _id;
    private String name;
    private String stock;
    private Integer cost;
}
