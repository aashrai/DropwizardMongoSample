package com.aashrai.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class OrderExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof OrderException) {
            OrderException orderException = (OrderException) exception;
            return Response.status(orderException.getStatus())
                    .entity(new OrderExceptionInfo(orderException.getStatus(), orderException.getMessage()))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
