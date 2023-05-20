package ru.filenko.services;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

public interface AuthService {
    Uni<Response> verifyUser(String login, String password);
    NewCookie generateCookieSession();
}
