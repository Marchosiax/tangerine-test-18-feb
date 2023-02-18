package com.marchosiax.tangerine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicates that the requested product was not found
 * It returns HTTP response with the status of 404
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product with id " + id + " was not found");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
