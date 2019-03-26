package com.aashrai.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.ws.rs.core.Response.Status;

@Data
@AllArgsConstructor
public class OrderExceptionInfo {
    private final Status statusCode;
    private final String message;
}
