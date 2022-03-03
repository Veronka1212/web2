package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BillDTO {
    Integer id;
    Integer room;
    String email;
    String cost;
    Boolean paymentState;
}
