package client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "session-source")
@Path("/session")
public interface SessionSource {
    @GET
    @Path("/create-session")
    Uni<String> createSession(@QueryParam("key") String key, @QueryParam("value") String value);

    @GET
    @Path("/get-username-from-session")
    Uni<String> getUsernameFromSession(@QueryParam("key") String key);

    @GET
    @Path("/validate-session")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<Boolean> validateSession(@QueryParam("key") String key);
}
