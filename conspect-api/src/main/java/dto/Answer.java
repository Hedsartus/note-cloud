package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


@Data
@JsonSerialize
@JsonDeserialize
public class Answer  {
    private long id;
    private String title;
    private boolean correct;
}