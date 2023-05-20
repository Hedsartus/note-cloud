package ru.filenko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Question extends PanacheEntity{
    @Column(name = "type")
    private int type; // 1 - множественный, 2 - да/нет
    @ManyToOne
    @JoinColumn(name = "id_note")
    @JsonIgnore
    private Note note;
    @Column(name = "title")
    private String title;
    @Column(name = "correct")
    private boolean correct;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;
}
