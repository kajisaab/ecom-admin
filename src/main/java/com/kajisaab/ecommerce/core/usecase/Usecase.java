package com.kajisaab.ecommerce.core.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import org.springframework.http.ResponseEntity;


public interface Usecase<I extends  UsecaseRequest, U extends UsecaseResponse>{
    ResponseEntity<U> execute(I request) throws BadRequestException;
}
