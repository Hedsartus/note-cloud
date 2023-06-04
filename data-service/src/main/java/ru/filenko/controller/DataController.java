package ru.filenko.controller;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.filenko.model.Note;
import ru.filenko.model.User;

import java.util.List;

@Path("/notes")
@ApplicationScoped
public class DataController {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Note>> getRootNote(@QueryParam("id") Long idUser,
                                       @CookieParam("session") String session) {
        return User.getUserByLogin("admin").map(User::getNoteList);
    }
}
