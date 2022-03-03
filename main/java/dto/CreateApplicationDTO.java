package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateApplicationDTO {
    String id;
    String email;
    String bed;
    String type;
    String time;
    String status;
    String processing_status;
}
