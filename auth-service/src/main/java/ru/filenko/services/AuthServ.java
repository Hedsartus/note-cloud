package ru.filenko.services;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

public interface AuthServ {
    Uni<String> verifyUser(String login, String password);
    NewCookie generateCookieSession();
    Uni<Boolean> validate(String key);
}
