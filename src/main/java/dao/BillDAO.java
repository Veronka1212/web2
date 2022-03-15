package dao;

import entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BillDAO {

    List<Bill> findByEmail(String email);

    Integer save(Bill entity) throws SQLException;

    Bill create(ResultSet resultSet);

    void pay(Integer id);

    void delete(Integer id);

    boolean isPaid(Integer id);

    Integer getRoomNumber(Integer id);

    Optional<Bill> findById(Integer id);
}
