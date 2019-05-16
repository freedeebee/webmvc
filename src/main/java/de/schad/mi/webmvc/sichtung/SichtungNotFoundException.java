package de.schad.mi.webmvc.sichtung;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SichtungNotFoundException
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SichtungNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SichtungNotFoundException(String error) {
        super(error);
    }
}