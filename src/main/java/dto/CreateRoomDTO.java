package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRoomDTO {
    String id;
    String bed;
    String type;
    String price;
    String status;
    String cleaning;
}
