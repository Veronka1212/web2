package dto;

import entity.room.Bed;
import entity.room.Status;
import entity.room.Type;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoomDTO {
    int id;
    Bed bed;
    Type type;
    String price;
    Status status;
    Boolean cleaning;

}
