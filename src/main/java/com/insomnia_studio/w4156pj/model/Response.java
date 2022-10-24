package com.insomnia_studio.w4156pj.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    /*
    Usage:
    200: OK
    500: Internal Server Error
     */
    private int responseCode;

    private String responseMessage;

    private T responseData;
}
