package dao;

import entity.Checkout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CheckoutDAO {
    Checkout save(Checkout entity) throws SQLException;

    void delete(Integer id);

    List<Checkout> findAll();

    Checkout createCheckout(ResultSet resultSet);
}
