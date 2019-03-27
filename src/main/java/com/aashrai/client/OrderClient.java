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
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Slf4j
@AllArgsConstructor
public class OrderClient {

    private final OrderDao orderDao;
    private final AccountDao accountDao;
    private final InventoryDao inventoryDao;
    private final OrderInfoDao orderInfoDao;

    public OrderInfo createOrder(Order order) {
        validateOrder(order);

        //Order creation with a rollback to restore the stock count in case of failure
        try {
            orderDao.createOrder(order);
            return orderInfoDao.createOrderInfo(mapToOrderInfo(order));
        } catch (Exception e) {
            log.error("Order placement error", e);
            inventoryDao.incrementStock(order.getPid());
            throw e;
        }
    }

    private void validateOrder(Order order) {
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
                .offerPrice(inventory.getCost())
                .paidPrice(getPaidPrice(order, inventory))
                .orderPlacedOn(format.format(order.getDate()))
                .build();
    }

    private int getPaidPrice(Order order, Inventory inventory) {
        if (order.getDiscount() == null) {
            return inventory.getCost();
        }
        return (int) (inventory.getCost() - order.getDiscount() * inventory.getCost());
    }
}
