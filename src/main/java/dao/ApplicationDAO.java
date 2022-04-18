package dao;

import entity.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ApplicationDAO {
    List<Application> findAll();

    Application createApplication(ResultSet resultSet) throws SQLException;

    List<Application> findByEmail(String email);

    Optional<Application> findById(String id);

    Integer save(Application entity) throws SQLException;

    List<Application> getPendingApplication();

    void processApplication(Integer id);

    void statusApplication(Integer id, String status);

    void delete(Integer id);


}
