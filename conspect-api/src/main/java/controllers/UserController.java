package controllers;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;




@Path("register")
public class UserController {
    @Inject
    Template register;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return register.data("name", name);
    }
}
