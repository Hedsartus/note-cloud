package controllers;

import client.AuthService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;

@Path("/")
public class AuthController {
    @Inject
    @RestClient
    AuthService authService;

    @Inject
    Template editor;
    @GET
    @Path("/login")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public String loginForm() {
        return authService.getLoginPage();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> authenticateAndSetCookie(
            //@RestHeader("Desired-path") String desiredPath,
            @FormParam("login") String login,
            @FormParam("password") String password) {

        return authService.authenticate(login, password)
                .map(item -> {
                    if(item != null) {
                        return Response
                                .status(Response.Status.SEE_OTHER)
                                .header(HttpHeaders.LOCATION, "/")
                                .cookie(new NewCookie("session", item))
                                .build();
                    } else {
                        return Response
                                .status(Response.Status.FORBIDDEN)
                                .location(URI.create("/login"))
                                .build();
                    }
                });
    }

    @GET
    @Path("/test")
    public String test() {
        return "test";
    }

    @GET
    public String main() {
        return "main";
    }

    @GET
    @Path("/editor")
    public TemplateInstance editor() {
        return editor.data("note", "ewferg e <b>gioerg</b> erg erg fjkvndfjkbn");
    }
}
