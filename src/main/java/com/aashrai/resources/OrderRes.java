package com.aashrai.resources;

import com.aashrai.api.Order;
import com.aashrai.api.OrderInfo;
import com.aashrai.client.OrderClient;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("order")
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderRes {

    private final OrderClient orderClient;

    @POST
    public OrderInfo createOrder(@Valid Order order) {
        return orderClient.createOrder(order);
    }

}
