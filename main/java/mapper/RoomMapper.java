package mapper;

import dto.RoomDTO;
import entity.room.Room;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomMapper {

    public RoomDTO getFrom(Room room){
        return RoomDTO.builder()
                .id(room.getId())
                .bed(room.getBed())
                .type(room.getType())
                .price(room.getPrice())
                .status(room.getStatus())
                .cleaning(room.getCleaning())
                .build();
    }
}
