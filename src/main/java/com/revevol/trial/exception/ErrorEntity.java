package com.revevol.trial.exception;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ErrorEntity {

    private String message;
    private String url;
}
