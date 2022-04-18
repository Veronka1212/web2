package service;

import dto.BillDTO;
import dto.CreateBillDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class BillServiceTest {
    BillService billService = new BillService();
    CreateBillDTO billDTO = CreateBillDTO.builder().id("999").email("test@bills.com").cost("100").room("1").paymentState("false").build();

    @Test
    public void findByEmail() {
        Integer id = billService.create(billDTO);
        BillDTO billDTO1 = billService.findByEmail(billDTO.getEmail()).get(0);
        billService.delete(id);
        Assert.assertEquals(returnBillDTO(), billDTO1);
    }

    @Test
    public void create() {
        Integer id = billService.create(billDTO);
        Assert.assertEquals(returnBillDTO(), billService.findByEmail(billDTO.getEmail()).get(0));
        billService.delete(id);
    }

    @Test
    public void pay() {
        Integer id = billService.create(billDTO);
        billService.pay(id);
        Assert.assertTrue(billService.findById(id).get().getPaymentState());
        billService.delete(id);
    }

    @Test
    public void delete() {
        Integer id = billService.create(billDTO);
        billService.delete(id);
        Assert.assertEquals(Optional.empty(), billService.findById(id));
    }

    @Test
    public void getRoomNumbers() {
        Integer id = billService.create(billDTO);
        billService.pay(id);
        int room = billService.getRoomNumbers("test@bills.com").get(0);
        Assert.assertEquals(1, room);
        billService.delete(id);
    }

    private BillDTO returnBillDTO() {
        return BillDTO.builder()
                .id(999)
                .email("test@bills.com")
                .cost("100")
                .room(1)
                .paymentState(Boolean.FALSE)
                .build();
    }
}