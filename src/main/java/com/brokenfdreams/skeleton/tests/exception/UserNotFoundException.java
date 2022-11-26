package com.brokenfdreams.skeleton.tests.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "User with id - %d not found";

    public UserNotFoundException(long userId) {
        super(MESSAGE_FORMAT.formatted(userId));
    }
}
