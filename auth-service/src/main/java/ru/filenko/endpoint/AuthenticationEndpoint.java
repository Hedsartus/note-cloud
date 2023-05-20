package ru.filenko.endpoint;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.filenko.services.AuthService;

@Path("/login")
@PermitAll
public class AuthenticationEndpoint {
    @Inject
    AuthService authService;
    @Inject
    Template login;

    public AuthenticationEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> authenticate(@FormParam("login") String login, @FormParam("password") String password) {
        return authService.verifyUser(login, password);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLoginPage() {
        return login.instance();
    }
}
