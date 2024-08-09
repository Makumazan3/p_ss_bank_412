package com.bank.antifraud.exception_handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
    public class ErrorMessage {
        private String message;
        private int statusCode;
        private String error;
        private LocalDateTime timestamp;
    }
