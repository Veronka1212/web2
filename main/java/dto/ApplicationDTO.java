package dto;

import entity.room.Bed;
import entity.room.Type;
import lombok.*;

@Getter
@Builder
public class ApplicationDTO {

    private int id;
    private String email;
    private Bed bed;
    private Type type;
    private int time;
    private boolean status;
    private int processing_status;
}
