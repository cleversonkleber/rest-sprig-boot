package com.cleverson.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportMathOperationException extends RuntimeException{
    private static final long seriaLVersionUID=1L;


    public UnsupportMathOperationException(String pleaseSeANumeric) {
        super(pleaseSeANumeric);
    }
}
