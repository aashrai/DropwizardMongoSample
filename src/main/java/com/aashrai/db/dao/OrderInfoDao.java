package com.aashrai.db.dao;

import com.aashrai.api.OrderInfo;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

@AllArgsConstructor
public class OrderInfoDao {

    private final MongoCollection orderInfos;

    public OrderInfo createOrderInfo(OrderInfo orderInfo) {
        orderInfos.insert(orderInfo);
        return orderInfo;
    }

    public OrderInfo getOrderInfo(String orderId) {
        return getOrderInfo(new ObjectId(orderId));
    }

    private OrderInfo getOrderInfo(ObjectId orderId) {
        return orderInfos.findOne("{ orderId:# }", orderId).as(OrderInfo.class);
    }
}
