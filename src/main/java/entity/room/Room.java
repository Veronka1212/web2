package entity.room;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder

public class Room {
    private int id;
    private Bed bed;
    private Type type;
    private String price;
    private Status status;
    private Boolean cleaning;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
