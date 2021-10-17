package com.kyc.accountservice.exception;

public class FileStorageException extends Throwable {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
