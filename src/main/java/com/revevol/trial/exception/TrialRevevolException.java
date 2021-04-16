package com.revevol.trial.exception;

import lombok.Data;

@Data
public class TrialRevevolException extends RuntimeException{

    private int statusCode;

    public TrialRevevolException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public TrialRevevolException(String message, Throwable cause) {
        super(message, cause);
    }

}
