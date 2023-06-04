package client;

import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "auth-service")
@Path("/auth")
public interface AuthService {
    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    String getLoginPage();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<String> authenticate(@FormParam("login") String login, @FormParam("password") String password);

//    @GET
//    @Path("/validate")
//    Uni<Boolean> validate(@QueryParam("key") String key);

}
