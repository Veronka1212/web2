package mapper;

import dto.BillDTO;
import entity.Bill;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BillMapper {
    public BillDTO getFrom(Bill bill) {
        return BillDTO.builder()
                .id(bill.getId())
                .room(bill.getRoom())
                .email(bill.getEmail())
                .cost(bill.getCost())
                .paymentState(bill.getPaymentState())
                .build();
    }
}
