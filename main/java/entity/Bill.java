package entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class Bill {
    private Integer id;
    private Integer room;
    private String email;
    private String cost;
    private Boolean paymentState;
}
