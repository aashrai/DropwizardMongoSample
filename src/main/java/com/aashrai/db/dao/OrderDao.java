package com.aashrai.db.dao;

import com.aashrai.api.Order;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

public class OrderDao {

    private final MongoCollection orders;

    public OrderDao(MongoCollection orders) {
        this.orders = orders;
    }

    public Order createOrder(Order order) {
        ObjectId upsertedId = (ObjectId) orders.insert(order).getUpsertedId();
        return orders.findOne(upsertedId).as(Order.class);
    }
}
