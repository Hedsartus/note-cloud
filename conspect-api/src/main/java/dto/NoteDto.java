package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
@JsonDeserialize
public class NoteDto {
    Long id;
    Long parent;
    String title;
    String html;

    public NoteDto () {

    }
}
