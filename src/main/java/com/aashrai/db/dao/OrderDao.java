package com.aashrai.db.dao;

import com.aashrai.api.Order;
import lombok.AllArgsConstructor;
import org.jongo.MongoCollection;

import java.util.Date;

@AllArgsConstructor
public class OrderDao {

    private final MongoCollection orders;

    public Order createOrder(Order order) {
        orders.insert(order);
        order.setDate(new Date());
        return order;
    }
}
