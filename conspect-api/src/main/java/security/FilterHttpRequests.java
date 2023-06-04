package security;

import client.SessionSource;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FilterHttpRequests {
    public final static String SESSION_TAG = "session";
    public final static String REQUIRE_LOGIN = "/login";
    @RestClient
    SessionSource sessionSource;

    @RouteFilter(100)
    void filterRequest(RoutingContext rc) {
        if(!rc.request().path().equals(REQUIRE_LOGIN)) {

            Cookie cookies = rc.request().getCookie(SESSION_TAG);
            if (cookies != null) {
                sessionSource.validateSession(cookies.getValue())
                        .subscribe().with(item -> {
                            if (!item) {
                                goToAuth(rc);
                            } else {
                                rc.next();
                            }
                        });
            } else {
                goToAuth(rc);
            }
        } else {
            rc.next();
        }
    }

    private void goToAuth(RoutingContext context) {
        context.response().setStatusCode(302);
        context.response().putHeader("Location", REQUIRE_LOGIN);
        context.response().end();
    }
}
