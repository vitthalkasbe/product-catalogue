package org.oms.in.productcatalogue.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {



   @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException ex,WebRequest request) {
        ApiResponse apiResponse=new ApiResponse(ex.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> bindingErrors = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());

        ApiResponse apiResponse=new ApiResponse("Validations failed for fields",bindingErrors.toString(),LocalDateTime.now());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
