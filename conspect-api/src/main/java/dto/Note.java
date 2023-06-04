package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonSerialize
@JsonDeserialize
public class Note  {
    private long id;
    private long parent;
    private String title;
    private String html;
    private List<Question> questionsList = new ArrayList<>();

    public Note () {}
}