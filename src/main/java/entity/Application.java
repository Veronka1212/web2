package entity;

import entity.room.Bed;
import entity.room.Type;
import lombok.*;

import java.util.Objects;

@Data
@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Application {
    private Integer id;
    private String email;
    private Bed bed;
    private Type type;
    private int time;
    private boolean status;
    private int processing_status;

    public boolean getStatus() {
        return status;
    }
}
