package dao;

import entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BillDAO {
    Optional<Bill> findById(Integer id);

    List<Bill> findByEmail(String email);

    Bill save(Bill entity) throws SQLException;

    Bill create(ResultSet resultSet);

    void pay(Integer id);

    void delete(Integer id);

    boolean isPaied(Integer id);

    public Integer getRoomNumber(Integer id);
}
