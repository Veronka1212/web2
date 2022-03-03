package mapper;

import dto.CheckoutDTO;
import entity.Checkout;

public class CheckoutMapper {
    public CheckoutDTO getFrom(Checkout checkout) {
        return CheckoutDTO.builder()
                .id(checkout.getId())
                .room(checkout.getRoom())
                .build();
    }
}
