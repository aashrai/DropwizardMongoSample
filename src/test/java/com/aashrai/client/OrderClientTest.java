package com.aashrai.client;

import com.aashrai.api.Account;
import com.aashrai.api.Inventory;
import com.aashrai.api.Order;
import com.aashrai.api.OrderInfo;
import com.aashrai.db.dao.AccountDao;
import com.aashrai.db.dao.InventoryDao;
import com.aashrai.db.dao.OrderDao;
import com.aashrai.db.dao.OrderInfoDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class OrderClientTest {

    @Mock
    OrderDao orderDao;
    @Mock
    AccountDao accountDao;
    @Mock
    InventoryDao inventoryDao;
    @Mock
    OrderInfoDao orderInfoDao;

    @InjectMocks
    OrderClient orderClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RuntimeException.class)
    public void createOrderRollback() {
        Order order = Order.builder().accountId("accountId").pid("pid").build();
        Mockito.when(accountDao.getAccount(order.getAccountId())).thenReturn(new Account());
        Mockito.when(inventoryDao.getInventory(order.getPid())).thenReturn(Inventory.builder()._id(order.getPid()).build());
        Mockito.when(inventoryDao.decrementStock(order.getPid())).thenReturn(true);
        Mockito.when(orderDao.createOrder(any())).thenThrow(new RuntimeException());

        orderClient.createOrder(order);
        Mockito.verify(inventoryDao).incrementStock(order.getPid());
        Mockito.verify(inventoryDao, times(0)).decrementStock(order.getPid());
    }

    @Test
    public void createOrderDiscount() {
        Integer cost = 300;
        Float discount = 0.25f;

        Order order = Order.builder().accountId("accountId").pid("pid").discount(discount).date(new Date()).build();
        Mockito.when(accountDao.getAccount(order.getAccountId())).thenReturn(new Account());
        Mockito.when(inventoryDao.getInventory(order.getPid())).thenReturn(Inventory.builder()._id(order.getPid()).cost(cost).build());
        Mockito.when(inventoryDao.decrementStock(order.getPid())).thenReturn(true);

        orderClient.createOrder(order);
        ArgumentCaptor<OrderInfo> argumentCaptor = ArgumentCaptor.forClass(OrderInfo.class);
        Mockito.verify(orderInfoDao, times(1)).createOrderInfo(argumentCaptor.capture());
        Assert.assertEquals(cost, argumentCaptor.getValue().getOfferPrice());
        Assert.assertEquals(Integer.valueOf(225), argumentCaptor.getValue().getPaidPrice());

        order.setDiscount(null);
        orderClient.createOrder(order);
        Mockito.verify(orderInfoDao, times(2)).createOrderInfo(argumentCaptor.capture());
        Assert.assertEquals(cost, argumentCaptor.getValue().getOfferPrice());
        Assert.assertEquals(cost, argumentCaptor.getValue().getPaidPrice());
    }
}