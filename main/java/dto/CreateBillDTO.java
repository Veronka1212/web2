package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateBillDTO {
    String id;
    String room;
    String email;
    String cost;
    String paymentState;
}
