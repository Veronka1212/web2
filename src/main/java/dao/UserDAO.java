package dao;

import entity.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByEmailAndPass(String email, String password) throws SQLException;

    Optional<String> getEmail(Integer id) throws SQLException;

    Integer save(User entity) throws SQLException;

    Optional<User> findById(Integer id);

    void delete(Integer id);
}
