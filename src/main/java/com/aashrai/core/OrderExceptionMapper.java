package com.aashrai.core;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Slf4j
public class OrderExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof OrderException) {
            OrderException orderException = (OrderException) exception;
            log.info(orderException.getMessage());
            return Response.status(orderException.getStatus())
                    .entity(new OrderExceptionInfo(orderException.getStatus(), orderException.getMessage()))
                    .build();
        }
        log.error("Unexpected error in order API", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
