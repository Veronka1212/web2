package service;

import dao.CheckoutDAOimpl;
import dto.BillDTO;
import dto.CheckoutDTO;
import dto.CreateCheckoutDTO;
import entity.Checkout;
import mapper.CheckoutMapper;
import mapper.CreateCheckoutMapper;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CheckoutService {
    private final CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();
    private final CreateCheckoutMapper createCheckoutMapper = new CreateCheckoutMapper();
    private final CheckoutMapper checkoutMapper = new CheckoutMapper();

    public Integer create(CreateCheckoutDTO createCheckoutDTO) {
        Checkout checkout = createCheckoutMapper.mapFrom(createCheckoutDTO);
        checkoutDAOimpl.save(checkout);
        return checkout.getId();
    }

    public List<CheckoutDTO> findAll() {
        return checkoutDAOimpl.findAll().stream()
                .map(checkoutMapper::getFrom)
                .collect(toList());
    }

    public void delete(Integer id) {
        checkoutDAOimpl.delete(id);
    }
}
