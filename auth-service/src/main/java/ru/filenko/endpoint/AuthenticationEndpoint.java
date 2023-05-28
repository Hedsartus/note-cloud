package ru.filenko.endpoint;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.filenko.services.AuthServ;

@Path("/auth")
@PermitAll
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
    @PermitAll
   //@Produces(MediaType.APPLICATION_JSON)
    public Uni<String> authenticate(@FormParam("login") String login, @FormParam("password") String password) {
        return authService.verifyUser(login, password);
    }

//    @GET
//    @Path("/validate")
//    public Uni<Boolean> validate(@QueryParam("key") String key) {
//        return authService.validate(key);
//    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLoginPage() {
        return login.instance();
    }
}
