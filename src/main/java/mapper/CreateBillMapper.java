package mapper;

import dto.CreateBillDTO;
import entity.Bill;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateBillMapper implements Mapperalable<CreateBillDTO, Bill> {

    @Override
    public Bill mapFrom(CreateBillDTO object) {
        return Bill.builder()
                .id(Integer.parseInt(object.getId()))
                .room(Integer.parseInt(object.getRoom()))
                .email(object.getEmail())
                .cost(object.getCost())
                .paymentState(Boolean.getBoolean(object.getPaymentState()))
                .build();
    }
}
