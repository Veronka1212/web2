package dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class BillDTO {
    Integer id;
    Integer room;
    String email;
    String cost;
    Boolean paymentState;
}
