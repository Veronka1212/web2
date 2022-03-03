package mapper;

import dto.CreateRoomDTO;
import entity.room.Bed;
import entity.room.Room;
import entity.room.Status;
import entity.room.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateRoomMapper implements Mapperalable<CreateRoomDTO, Room> {

    @Override
    public Room mapFrom(CreateRoomDTO object) {
        return Room.builder()
                .id(Integer.parseInt(object.getId()))
                .bed(Bed.valueOf(object.getBed()))
                .type(Type.valueOf(object.getType()))
                .price(object.getPrice())
                .status(Status.valueOf(object.getStatus()))
                .cleaning(Boolean.valueOf(object.getCleaning()))
                .build();
    }
}
