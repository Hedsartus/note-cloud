package ru.filenko.security;

import ru.filenko.model.User;

public class AuthenticationContextImpl implements  AuthenticationContext{
    private User user;

    @Override
    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }
}
