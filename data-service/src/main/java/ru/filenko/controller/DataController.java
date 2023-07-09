package ru.filenko.controller;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import ru.filenko.model.Note;
import ru.filenko.model.User;

import java.util.List;

@Path("/notes")
@ApplicationScoped
public class DataController {
    @GET
    @Path("/all")
    public Uni<List<Note>> getRootNote(@QueryParam("login") String userLogin) {
        return User.getUserByLogin(userLogin).map(User::getNoteList);
    }
}
