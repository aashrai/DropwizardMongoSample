package com.aashrai.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private String userName;
    private String orderId;
    private String productName;
    private String orderPlacedOn;
}
