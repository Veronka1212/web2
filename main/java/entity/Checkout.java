package entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class Checkout {
    private Integer id;
    private Integer room;
}
