package ru.filenko.controller;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ru.filenko.model.Note;

import java.util.ArrayList;
import java.util.List;

@Path("/notes")
@ApplicationScoped
public class DataController {
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Note>> getRootNote() {
//        List<Note> list = new ArrayList<>();
//        Note.findAlive().subscribeAsCompletionStage().complete(list);
//
//        return list;


//        return Note.findAlive().map(list1 -> {
//            List<NoteDto> list2 = new ArrayList<>();
//            for (Note obj1 : list1) {
//                NoteDto obj2 = new NoteDto();
//                obj2.setId(obj1.id);
//                obj2.setTitle(obj1.getTitle());
//                obj2.setHtml(obj1.getHtml());
//                obj2.setParent(obj1.getParent());
//
//                list2.add(obj2);
//            }
//            return list2;
//        });

        return Note.findAlive();

    }

    @GET
    @Path("/ooo")
    public String getTest() {
        return "TEST";
    }
}
