package service;

import dto.BillDTO;
import dto.CheckoutDTO;
import dto.CreateCheckoutDTO;
import entity.Checkout;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CheckoutServiceTest {
    CheckoutService checkoutService = new CheckoutService();
    CreateCheckoutDTO checkoutDTO = CreateCheckoutDTO.builder().room("99").build();

    @Test
    public void create() {
        Integer id = checkoutService.create(checkoutDTO);
        Assert.assertEquals(Optional.of(returnCheckout(id)), checkoutService.findById(id));
        checkoutService.delete(id);
    }

    @Test
    public void findAll() {
        Integer id = checkoutService.create(checkoutDTO);
        Integer id2 = checkoutService.create(checkoutDTO);
        Integer id3 = checkoutService.create(checkoutDTO);
        List<CheckoutDTO> list = new ArrayList<>();
        list.add(CheckoutDTO.builder().id(id).room(99).build());
        list.add(CheckoutDTO.builder().id(id2).room(99).build());
        list.add(CheckoutDTO.builder().id(id3).room(99).build());
        Assert.assertEquals(list,checkoutService.findAll());
        checkoutService.delete(id);
        checkoutService.delete(id2);
        checkoutService.delete(id3);
    }

    @Test
    public void delete() {
        Integer id = checkoutService.create(checkoutDTO);
        checkoutService.delete(id);
        Assert.assertEquals(Optional.empty(), checkoutService.findById(id));
    }
    private Checkout returnCheckout(Integer id) {
        return Checkout.builder()
                .id(id)
                .room(99)
                .build();
    }
}