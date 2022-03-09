package dao;

import entity.Application;
import entity.Bill;
import jdbc.BasicConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class BillDAOimplTest {

    BillDAOimpl billDAOimpl = new BillDAOimpl();
    private static final String SEARCH_BILL = "SELECT * FROM bill WHERE room = 1 AND cost = '100' AND paymentState = false AND email = 'email';";

    @Test
    public void findById() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Assert.assertEquals(bill, billDAOimpl.findById(1).get());
            }
            billDAOimpl.delete(bill.getId());
        }
    }

    @Test
    public void findByEmail() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Assert.assertEquals(bill, billDAOimpl.findByEmail("email"));
            }
            billDAOimpl.delete(bill.getId());
        }
    }

    @Test
    public void isPaid() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Assert.assertFalse(bill.getPaymentState());
            }
            billDAOimpl.delete(bill.getId());
        }
    }

    @Test
    public void save() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Bill bill1 = billDAOimpl.create(resultSet);
                billDAOimpl.delete(bill.getId());
                Assert.assertEquals(bill, bill1);
            }
        }
    }

    @Test
    public void create() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Assert.assertEquals(bill, billDAOimpl.create(resultSet));
            }
            billDAOimpl.delete(bill.getId());
        }
    }

    @Test
    public void pay() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Bill bill2 = billDAOimpl.create(resultSet);
                Integer id = bill2.getId();
                billDAOimpl.pay(id);
                bill2 = billDAOimpl.findById(id).get();
                billDAOimpl.delete(bill.getId());
                Assert.assertTrue(bill2.getPaymentState());
            }
        }
    }

    @Test
    public void delete() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            Bill bill = getBill();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bill = billDAOimpl.create(resultSet);
                Integer id = bill.getId();
                billDAOimpl.delete(id);
                Assert.assertFalse(billDAOimpl.findById(id).isPresent());
            }
        }
    }

    @Test
    public void getRoomNumber() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Bill bill = getBill();
            if (resultSet.next()) {
                Assert.assertEquals(Optional.of(1), billDAOimpl.getRoomNumber(bill.getId()));
            }
            billDAOimpl.delete(bill.getId());
        }
    }

    private Bill getBill() {
        Bill bill = Bill.builder()
                .id(1)
                .room(1)
                .cost("100")
                .paymentState(false)
                .email("email")
                .build();
        billDAOimpl.save(bill);
        return bill;
    }
}