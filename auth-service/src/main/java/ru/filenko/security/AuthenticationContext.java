package ru.filenko.security;

import ru.filenko.model.User;

public interface AuthenticationContext {
    User getCurrentUser();
}
