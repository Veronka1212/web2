package entity.room;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Room {
    private int id;
    private Bed bed;
    private Type type;
    private String price;
    private Status status;
    private Boolean cleaning;

}
