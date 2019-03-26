package com.aashrai.db.dao;

import com.aashrai.api.Order;
import org.jongo.MongoCollection;

public class OrderDao {

    private final MongoCollection orders;

    public OrderDao(MongoCollection orders) {
        this.orders = orders;
    }

    public Order createOrder(Order order) {
        orders.insert(order);
        return order;
    }
}
