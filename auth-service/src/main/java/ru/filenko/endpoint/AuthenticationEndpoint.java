package ru.filenko.endpoint;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.filenko.services.AuthServ;

@Path("/auth")
public class AuthenticationEndpoint {
    @Inject
    AuthServ authService;
    @Inject
    Template login;

    public AuthenticationEndpoint(AuthServ authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> authenticate(@FormParam("login") String login, @FormParam("password") String password) {
        return authService.verifyUser(login, password);
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLoginPage() {
        return login.instance();
    }
}
