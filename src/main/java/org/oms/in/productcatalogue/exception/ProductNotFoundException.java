package org.oms.in.productcatalogue.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException()
    {
    }

    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
