package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;


@Data
@JsonSerialize
@JsonDeserialize
public class Question {
    private long id;
    private int type; // 1 - множественный, 2 - да/нет
    private String title;
    private boolean correct;
    private List<Answer> answers;

    Question() {
    }
}