package dao;

import entity.Checkout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CheckoutDAO {
    Integer save(Checkout entity) throws SQLException;

    void delete(Integer id);

    List<Checkout> findAll();

    Checkout createCheckout(ResultSet resultSet);

    Optional<Checkout> findById(Integer id);
}
