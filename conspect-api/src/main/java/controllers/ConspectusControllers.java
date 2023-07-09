package controllers;

import client.DataService;
import client.SessionSource;
import dto.Note;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@Path("/")
public class ConspectusControllers {
    @RestClient
    DataService dataService;

    @RestClient
    SessionSource sessionSource;

    @GET
    @Path("/notes")
    public Uni<List<Note>> getRootNote(@CookieParam("session") String session) {
        return sessionSource.getUsernameFromSession(session)
                .onItem().ifNull().failWith(new Exception("Error session!"))
                .onItem().transformToUni(login -> dataService.getRootNote(login, session));
    }
}
