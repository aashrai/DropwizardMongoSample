package com.aashrai.client;

import com.aashrai.api.Account;
import com.aashrai.api.Inventory;
import com.aashrai.api.Order;
import com.aashrai.db.dao.AccountDao;
import com.aashrai.db.dao.InventoryDao;
import com.aashrai.db.dao.OrderDao;
import com.aashrai.db.dao.OrderInfoDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

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
        Mockito.verify(inventoryDao, Mockito.times(0)).decrementStock(order.getPid());
    }
}