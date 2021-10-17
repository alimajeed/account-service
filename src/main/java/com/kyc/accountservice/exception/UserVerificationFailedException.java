package com.kyc.accountservice.exception;

public class UserVerificationFailedException extends RuntimeException {
    public UserVerificationFailedException(String message) {
        super(message);
    }

    public UserVerificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
