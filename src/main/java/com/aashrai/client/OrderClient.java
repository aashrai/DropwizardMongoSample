package com.aashrai.client;

import com.aashrai.api.Account;
import com.aashrai.api.Inventory;
import com.aashrai.api.Order;
import com.aashrai.core.OrderException;
import com.aashrai.db.dao.AccountDao;
import com.aashrai.db.dao.InventoryDao;
import com.aashrai.db.dao.OrderDao;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class OrderClient {

    private final OrderDao orderDao;
    private final AccountDao accountDao;
    private final InventoryDao inventoryDao;

    public OrderClient(OrderDao orderDao,
                       AccountDao accountDao,
                       InventoryDao inventoryDao) {
        this.orderDao = orderDao;
        this.accountDao = accountDao;
        this.inventoryDao = inventoryDao;
    }

    public Order createOrder(Order order) {
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

        return orderDao.createOrder(order);
    }
}
