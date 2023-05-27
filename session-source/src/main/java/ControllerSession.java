import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/session")
@ApplicationScoped
public class ControllerSession {
    @Inject
    CookieSource cookieSource;

    @GET
    @Path("/create-session")
    //@Produces(MediaType.APPLICATION_JSON)
    public Uni<String> createSession(@QueryParam("key") String key, @QueryParam("value") String value) {
        cookieSource.createSession(key, value);
        return cookieSource.getValue(key);
    }

//    @GET
//    @Path("/get-username-from-session")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Uni<String> getUsernameFromSession(String key) {
//        return cookieSource.getUsernameFromSession(key);
//    }
//
//    @GET
//    @Path("/validate-session")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Uni<Boolean> validateSession(String key) {
//        return cookieSource.validateSession(key);
//    }
}
