package controllers;

import client.AuthService;
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
    private final String PATH_LOGIN = "/login";
    @Inject
    @RestClient
    AuthService authService;

    @GET
    @Path(PATH_LOGIN)
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public String loginForm() {
        return authService.getLoginPage();
    }

    @POST
    @Path(PATH_LOGIN)
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Response> authenticateAndSetCookie(
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
                                .location(URI.create(PATH_LOGIN))
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
    @Produces(MediaType.TEXT_HTML)
    public String main() {
        return "main <br> <a href='/notes'>notes</a>";
    }
}
