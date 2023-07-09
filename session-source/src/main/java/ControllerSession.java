import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/session")
@ApplicationScoped
public class ControllerSession {
    @Inject
    CookieSource cookieSource;

    @GET
    @Path("/create-session")
    public Uni<String> createSession(@QueryParam("key") String key, @QueryParam("value") String value) {
        cookieSource.createSession(key, value).subscribeAsCompletionStage().thenApply(status->"");
        return cookieSource.getValue(key);
    }

    @GET
    @Path("/get-username-from-session")
    public Uni<String> getUsernameFromSession(@QueryParam("key") String key) {
        return cookieSource.getUsernameFromSession(key);
    }

    @GET
    @Path("/validate-session")
    public Uni<Boolean> validateSession(@QueryParam("key") String key) {
        return cookieSource.validateSession(key);
    }
}
