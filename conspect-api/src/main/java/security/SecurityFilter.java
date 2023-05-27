package security;

import client.SessionSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Map;

@Provider
@PreMatching
@ApplicationScoped
@Slf4j
public class SecurityFilter implements ContainerRequestFilter {
    public final static String SESSION_TAG = "session";
    public final static String REQUIRE_LOGIN = "/auth/login";

    @RestClient
    SessionSource sessionSource;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Map<String, Cookie> cookies = requestContext.getCookies();

        if(cookies.containsKey(SESSION_TAG)) {
            sessionSource.validateSession(cookies.get(SESSION_TAG).getValue()).map(isTrueKey ->{
                if(!isTrueKey) {
                    requestContext.setMethod(REQUIRE_LOGIN);
                    log.info("_____Point 1");
                }
                log.info("_____Point 2");
                return null;
            }).subscribeAsCompletionStage().thenApply(status->"successful");

        } else {
            log.info("_____Point 3");
            try {
                requestContext.getHeaders().add("Desired-path", requestContext.getUriInfo().getPath());
                requestContext.setRequestUri(new URI(REQUIRE_LOGIN));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
