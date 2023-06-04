package controllers;

import client.DataService;
import dto.Note;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@Path("/n")
public class ConspectusControllers {
    @RestClient
    DataService dataService;

    @GET
    @Path("/notes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Note>> getRootNote(
            @CookieParam("session") String session,
            @QueryParam("id") Long idUser) {
        return dataService.getRootNote(idUser, session);
    }
}
