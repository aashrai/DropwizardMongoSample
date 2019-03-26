package com.aashrai.client;

import com.aashrai.api.Account;
import com.aashrai.api.Inventory;
import com.aashrai.api.Order;
import com.aashrai.api.OrderInfo;
import com.aashrai.core.OrderException;
import com.aashrai.db.dao.AccountDao;
import com.aashrai.db.dao.InventoryDao;
import com.aashrai.db.dao.OrderDao;
import com.aashrai.db.dao.OrderInfoDao;
import lombok.AllArgsConstructor;

import java.text.SimpleDateFormat;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@AllArgsConstructor
public class OrderClient {

    private final OrderDao orderDao;
    private final AccountDao accountDao;
    private final InventoryDao inventoryDao;
    private final OrderInfoDao orderInfoDao;

    public OrderInfo createOrder(Order order) {
        Account account = accountDao.getAccount(order.getAccountId());
        if (account == null) {
            throw new OrderException(NOT_FOUND, String.format("Account: %s not found", order.getAccountId()));
        }

        Inventory inventory = inventoryDao.getInventory(order.getPid());
        if (inventory == null) {
            throw new OrderException(NOT_FOUND, String.format("Inventory: %s not found", order.getPid()));
        }

        Boolean stockDecrement = inventoryDao.decrementStock(inventory.get_id());
        if (!stockDecrement) {
            throw new OrderException(BAD_REQUEST, String.format("Inventory: %s does not have enough stock", inventory.get_id()));
        }

        orderDao.createOrder(order);
        return orderInfoDao.createOrderInfo(mapToOrderInfo(order));
    }

    private OrderInfo mapToOrderInfo(Order order) {
        Inventory inventory = inventoryDao.getInventory(order.getPid());
        Account account = accountDao.getAccount(order.getAccountId());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return OrderInfo.builder()
                .orderId(order.get_id())
                .userName(account.getName())
                .productName(inventory.getName())
                .address(account.getAddress())
                .paidPrice(inventory.getCost())
                .orderPlacedOn(format.format(order.getDate()))
                .build();
    }
}
