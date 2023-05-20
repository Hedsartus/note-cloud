package controllers;

import client.DataService;
import dto.NoteDto;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Objects;

@Path("/")
public class ConspectControllers {
    @Inject
    @RestClient
    DataService dataService;

    @GET
    @RolesAllowed("admin")
    @Path("/notes")
    public Uni<List<NoteDto>> getRootNote() {
        return dataService.getRootNote();
    }
}
