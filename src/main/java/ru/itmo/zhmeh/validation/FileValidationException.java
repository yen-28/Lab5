package ru.itmo.zhmeh.validation;

/**
 * Возникает при ошибках валидации файла
 */
public class FileValidationException extends Exception {
    public FileValidationException(String message) {
        super(message);
    }

    public FileValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
