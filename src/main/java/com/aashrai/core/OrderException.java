package com.aashrai.core;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response.Status;
import java.io.Serializable;

public class OrderException extends ProcessingException implements Serializable {
    private final Status status;
    private final String message;

    private OrderException() {
        this(null, null);
    }

    public OrderException(Status status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "OrderException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
