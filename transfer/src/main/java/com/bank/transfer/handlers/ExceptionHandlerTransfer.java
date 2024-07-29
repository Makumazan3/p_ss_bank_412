package com.bank.transfer.handlers;

import com.bank.transfer.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс-обработчик исключений, необходимый для
 * обработки ситуации, когда объект не был найден.
 **/

@RestControllerAdvice
public class ExceptionHandlerTransfer {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerTransfer.class);

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public EntityNotFoundException handler (EntityNotFoundException ignoredE) {
        logger.error("Not found");
        return new EntityNotFoundException();
    }

}
