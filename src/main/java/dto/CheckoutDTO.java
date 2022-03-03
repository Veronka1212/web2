package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CheckoutDTO {
    Integer id;
    Integer room;
}
