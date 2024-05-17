package com.kajisaab.ecommerce.core.responseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseHandler<O> {

    public static <T> ResponseEntity<T> responseBuilder(Object responseObject){
        return responseBuilder("SUCCESS", HttpStatus.OK, responseObject);
    }

    public static <T> ResponseEntity<T> responseBuilder(String message, HttpStatus httpStatus, Object responseObject){
        ResponseHandlerService response = new ResponseHandlerService(httpStatus.value(), message, responseObject);

        return new ResponseEntity<T>((T) response, httpStatus);
    }
}