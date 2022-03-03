package mapper;

import dto.CreateCheckoutDTO;
import entity.Checkout;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateCheckoutMapper implements Mapperalable<CreateCheckoutDTO, Checkout> {
    @Override
    public Checkout mapFrom(CreateCheckoutDTO object) {
        return Checkout.builder()
                .room(Integer.parseInt(object.getRoom()))
                .build();
    }
}
