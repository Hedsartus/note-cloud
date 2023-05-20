package ru.filenko.services;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.filenko.model.User;

import java.util.UUID;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    @ConfigProperty(name = "authentication.session.expiration")
    Long sessionExpiration;
    private final ReactiveValueCommands<String, String> valueCommands;

    public AuthServiceImpl(ReactiveRedisDataSource reactive) {
        this.valueCommands = reactive.value(String.class, String.class);
    }

    @Override
    @WithSession
    public Uni<Response> verifyUser(String login, String password) {
        return User.find("login = ?1 and password = ?2", login, password)
                .firstResult()
                .map(user -> true)
                .map(valid -> {
                    if (valid) {
                        NewCookie cookie = generateCookieSession();

                        valueCommands.setex("rkey", sessionExpiration, cookie.getValue())
                                .subscribeAsCompletionStage().thenApply(status-> "Successfully");

                        return Response.ok().cookie(cookie).build();
                    } else {
                        return Response.status(Response.Status.FORBIDDEN).build();
                    }
                });
    }


//

//
//    @Inject
//    SecurityIdentity identity;
//

//
//    public Response verifyUser(String login, String password) {
//        Uni<User> user = getUser(login);
//
//        return user
//                .onItem().transform(u -> u != null && u.getPassword().equals(password))
//                .onItem().transform(valid -> {
//                    if (valid) {
//                        String session = generateCookieWithSession();
//                        reactiveDataSource.set(String.class, String.class);
//                        //reactiveDataSource.
//                        NewCookie cookie = new NewCookie("session", session);
//                        return Response.ok().cookie(cookie).build();
//                    } else {
//                        return Response.status(RestResponse.Status.FORBIDDEN).build();
//                    }
//                }).await().indefinitely();
//    }


//    public Uni<Response> login(@CookieParam("session") String sessionId) {
//        return Uni.createFrom().item(sessionId)
//                .onItem().ifNotNull().transformToUni(this::validateSession)
//                .onItem().ifNotNull().transform(sess -> Response.ok("User authenticated").build())
//                .onItem().ifNull().continueWith("erferf")
//                .map(sess -> Response.ok("User authenticated")
//                        .cookie(new NewCookie("session", sessionId))
//                        .build());
//    }


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

//    public Uni<String> validateSession(String sessionId) {
//        return reactiveDataSource.key().keys(sessionId).map(value -> value != null ? sessionId : null);
//    }

//    private Uni<String> generateAndSaveSession(String sessionId) {
//        if (sessionId == null) {
//            sessionId = generateSessionId();
//        }
//
////        keyCommands.getDataSource().set(String.class, String.class).sadd(sessionId, "authenticated").;
//
////        setCommands.sadd(sessionId, "authenticated").flatMap(
////                result-> setCommands.ex
////        );
//
////        return reactiveDataSource.set(sessionId, "authenticated")
////                .flatMap(result -> keyCommands.expire(sessionId, sessionExpiration))
////                .map(result -> sessionId);
////        String finalSessionId = sessionId;
////        return reactiveDataSource.set(sessionId, "authenticated")
////                .flatMap(result -> keyCommands.expire(finalSessionId, sessionExpiration))
////                .map(result -> result);
//
//        Uni<Boolean> expire (K key,long seconds, ExpireArgs expireArgs);
//    }

    //    private void deleteSession(String sessionId) {
//        keyCommands.del(sessionId).replaceWithVoid();
//    }
//
    @Override
    public NewCookie generateCookieSession() {
        return new NewCookie("session", UUID.randomUUID().toString());
    }

}
