package com.aashrai.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * This document acts as a snapshot of details from multiple documents at the time of order placement,
 * Since productCost, address etc can change in the future it is important we store these seperately
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    @MongoObjectId
    private String orderId;
    private String address;
    private String userName;
    private String productName;
    private String orderPlacedOn;
    private Integer paidPrice;
    private Integer offerPrice;
}
