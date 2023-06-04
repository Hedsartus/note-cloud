package ru.filenko.security;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.filenko.client.SessionSource;

@ApplicationScoped
public class FilterHttpRequests {
    public final static String SESSION_TAG = "session";
    @RestClient
    SessionSource sessionSource;

    @RouteFilter(100)
    void filterRequest(RoutingContext rc) {
        Cookie cookies = rc.request().getCookie(SESSION_TAG);
        if (cookies != null) {
            sessionSource.validateSession(cookies.getValue())
                    .subscribe().with(item -> {
                        if (!item) {
                            badRequest(rc);
                        } else {
                            rc.next();
                        }
                    });
        } else {
            badRequest(rc);
        }
    }

    private void badRequest(RoutingContext context) {
        context.response().setStatusCode(401);
        context.response().end();
    }
}
