package client;

import dto.Note;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@ApplicationScoped
@RegisterRestClient(configKey = "data-service")
@Path("/notes")
public interface DataService {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<Note>> getRootNote(@QueryParam("id") Long idUser, @CookieParam("session") String session);
}
