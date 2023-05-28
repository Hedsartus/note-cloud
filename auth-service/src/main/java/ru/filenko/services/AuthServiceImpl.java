package ru.filenko.services;


import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.filenko.client.SessionSource;
import ru.filenko.model.User;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
public class AuthServiceImpl implements AuthServ {
    public static final String SPLITTER = ":";
    @RestClient
    SessionSource sessionSource;

    @Override
    @WithSession
    public Uni<String> verifyUser(String login, String password) {
        return User.find("login = ?1 and password = ?2", login, password)
                .firstResult()
                .map(user -> true)
                .map(valid -> {
                    if (valid) {
                        NewCookie cookie = generateCookieSession();

                        String session = cookie.getValue()+SPLITTER+
                                Base64.getEncoder().encodeToString(login.getBytes(StandardCharsets.UTF_8));

                        sessionSource.createSession(cookie.getValue(), session).subscribeAsCompletionStage()
                                .thenApply(status-> "");

                        return cookie.getValue();
                    } else {
                        return null;
                    }
                });
    }




//    @GET
//    @Path("/logout")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Uni<Response> logout(@CookieParam("session") String sessionId) {
//        return Uni.createFrom().item(sessionId)
//                .onItem().ifNotNull().invoke(this::deleteSession)
//                .map(sess -> Response.ok("User logged out")
//                        .cookie(new NewCookie("session", null, null, null, null, 0, false))
//                        .build())
//                .onItem().ifNull().continueWith(() -> Response.ok("User logged out").build());
//    }
//
//                        return Response
//                                .status(Response.Status.SEE_OTHER)
//                                .cookie(cookie)
//                                .location(URI.create("/"))
//                                .build();

    @Override
    public NewCookie generateCookieSession() {
        return new NewCookie("session", UUID.randomUUID().toString());
    }

    @Override
    public Uni<Boolean> validate(String key) {
        return sessionSource.validateSession(key);
    }

}
