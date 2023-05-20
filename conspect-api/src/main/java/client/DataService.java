package client;

import dto.NoteDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@ApplicationScoped
@RegisterRestClient(configKey = "data-service")
@Path("/notes")
public interface DataService {
    @GET
    @Path("/all")
    Uni<List<NoteDto>> getRootNote();
//
//    @GET
//    @Path("/notes/{idNote}")
//    Uni<List<Note>> getNote();

    @GET
    @Path("/ooo")
    String getTest();


}
