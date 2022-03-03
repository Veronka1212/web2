package service;

import dao.BillDAOimpl;
import dao.RoomDAOimpl;
import dto.BillDTO;
import dto.CreateBillDTO;
import entity.Bill;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mapper.BillMapper;
import mapper.CreateBillMapper;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public class BillService {

    private final BillDAOimpl billDAOimpl = new BillDAOimpl();
    private final CreateBillMapper createBillMapper = new CreateBillMapper();
    private final BillMapper billMapper = new BillMapper();

    public List<BillDTO> findByEmail(String email) {
        return billDAOimpl.findByEmail(email).stream()
                .map(billMapper::getFrom)
                .collect(toList());
    }

    public Integer create(CreateBillDTO createBillDTO) {
        Bill bill = createBillMapper.mapFrom(createBillDTO);
        billDAOimpl.save(bill);
        return bill.getId();
    }

    public void pay(Integer id) {
        billDAOimpl.pay(id);
    }

    public void delete(Integer id) {
        billDAOimpl.delete(id);
    }

    public List<Integer> getRoomNumbers(String email) {
        return billDAOimpl.findByEmail(email).stream()
                .map(billMapper::getFrom)
                .filter(BillDTO::getPaymentState)
                .map(BillDTO::getRoom)
                .collect(toList());
    }
}
