package ru.filenko.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "notes")
public class Note extends PanacheEntity   {
    @Column(name = "parent_id", columnDefinition = "bigint default 0")
    private Long parent;
    @Column(name="title", length = 200)
    private String title;
    @Column(name = "html")
    private String html;

    @OneToMany(mappedBy = "note", fetch = FetchType.EAGER)
    private List<Question> questionsList = new ArrayList<>();

    public Note () {}
    public static Uni<List<Note>> findAlive(){
        return list("parent = ?1", 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        if (!title.equals(note.title)) return false;
        return Objects.equals(html, note.html);
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}