package entity;

import entity.room.Bed;
import entity.room.Type;
import lombok.*;

import java.util.Objects;

@Data
@Getter
@Builder
public class Application {
    private Integer id;
    private String email;
    private Bed bed;
    private Type type;
    private int time;
    private boolean status;
    private int processing_status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean getStatus() {
        return status;
    }
}
