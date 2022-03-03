package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCheckoutDTO {
    String id;
    String room;
}
