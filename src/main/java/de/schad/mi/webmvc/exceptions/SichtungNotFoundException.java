package de.schad.mi.webmvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SichtungNotFoundException is thrown when an Observation could not be found
 * Its ResponseStatus is Http not found (404)
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SichtungNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SichtungNotFoundException(String error) {
        super(error);
    }
}