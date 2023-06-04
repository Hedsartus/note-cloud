package ru.filenko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "answers")
@Data
public class Answer extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "id_question")
    @JsonIgnore
    private Question question;
    @Column(name = "title")
    private String title;
    @Column(name = "correct")
    private boolean correct;
}