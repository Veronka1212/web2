package dao;

import entity.Application;
import entity.Bill;
import entity.Checkout;
import jdbc.BasicConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckoutDAOimplTest {

    CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();
    private static final String SEARCH_CHECKOUT = "SELECT * FROM checkout WHERE room = '1';";

    @Test
    public void save() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CHECKOUT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Checkout checkout = getCheckout();
            if (resultSet.next()) {
                Assert.assertEquals(checkout, checkoutDAOimpl.createCheckout(resultSet));
            }
            checkoutDAOimpl.delete(checkout.getId());
        }
    }

    @Test
    public void delete() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CHECKOUT)) {
            getCheckout();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Checkout checkout = checkoutDAOimpl.createCheckout(resultSet);
                Integer id = checkout.getId();
                checkoutDAOimpl.delete(id);
                Assert.assertFalse(checkoutDAOimpl.findById(id).isPresent());
            }
        }
    }

    @Test
    public void findAll() {
        List<Checkout> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Checkout checkout = Checkout.builder().room(i).build();
            checkoutDAOimpl.save(checkout);
            list.add(checkout);
        }
        List<Checkout> list2 = checkoutDAOimpl.findAll();
        Assert.assertEquals(list.size(), list2.size());
        for (int i = 0; i < 3; i++) {
            checkoutDAOimpl.delete(list2.get(i).getId());
        }
    }

    @Test
    public void createCheckout() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CHECKOUT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Checkout checkout = getCheckout();
            if (resultSet.next()) {
                Assert.assertEquals(checkout, checkoutDAOimpl.createCheckout(resultSet));
            }
            checkoutDAOimpl.delete(checkout.getId());
        }
    }

    private Checkout getCheckout() {
        Checkout checkout = Checkout.builder()
                .id(1)
                .room(1)
                .build();
        checkoutDAOimpl.save(checkout);
        return checkout;
    }
}