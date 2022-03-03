package dao;

import entity.Application;
import entity.room.Bed;
import entity.room.Type;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class ApplicationDAOimpl implements ApplicationDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String FIND_ALL = "SELECT * FROM application;";
    private static final String SAVE_APPLICATION =
            "INSERT INTO application (email, bed, type, time, status, processing_status) VALUES (?,?,?,?,?,?) ";
    private static final String PENDING_APPLICATION =
            "SELECT * FROM my_hotel.application WHERE processing_status=0;";
    private static final String UPDATE_APPLICATION =
            "UPDATE application SET processing_status='1' WHERE id=?;";
    private static final String UPDATE_STATUS =
            "UPDATE application SET status=? WHERE id=?;";
    private static final String DELETE =
            "DELETE FROM application WHERE id=?;";

    @Override
    public List<Application> findAll() {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Application> applications = new ArrayList<>();
            while (resultSet.next()) {
                applications.add(createApplication(resultSet));
            }
            logger.info("Return all applications");
            return applications;
        } catch (SQLException e) {
            logger.error("Error creating list of all orders");
            throw new DaoException(e);

        }
    }

    @Override
    public Application createApplication(ResultSet resultSet) {
        Application application;
        try {
            String status = resultSet.getObject("status", String.class);
            if (status.equals(ONE)) {
                status = "true";
            }
            application = Application.builder().
                    id(resultSet.getObject("id", Integer.class)).
                    email(resultSet.getObject("email", String.class)).
                    bed(Bed.valueOf(resultSet.getObject("bed", String.class))).
                    type(Type.valueOf(resultSet.getObject("type", String.class))).
                    time(resultSet.getObject("time", Integer.class)).
                    status(Boolean.valueOf(status)).
                    processing_status(resultSet.getObject("processing_status", Integer.class))
                    .build();
            logger.info("Application object creation was successful");
            return application;
        } catch (SQLException e) {
            logger.error("Error create application from data base");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Application> findByEmail(String email) {
        List<Application> applications = new ArrayList<>();
        Application application;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                application = createApplication(resultSet);
                if (application.getEmail().equals(email)) {
                    applications.add(application);
                }
            }
            logger.info("Applications fond by email");
            return applications;
        } catch (SQLException e) {
            logger.error("Error find by email");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Application> findById(String id) {
        Application application = Application.builder().build();
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                application = createApplication(resultSet);
                if (application.getId().equals(Integer.parseInt(id))) {
                    return Optional.of(application);
                }
            }
            logger.info("Application fond by ID");
            return Optional.ofNullable(application);
        } catch (SQLException e) {
            logger.error("Error find by ID");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Application save(Application entity) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_APPLICATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getEmail());
            preparedStatement.setObject(2, entity.getBed().name());
            preparedStatement.setObject(3, entity.getType().name());
            preparedStatement.setObject(4, entity.getTime());
            preparedStatement.setObject(5, entity.getStatus());
            preparedStatement.setObject(6, entity.getProcessing_status());
            preparedStatement.executeUpdate();
            logger.info("Application object saved in the database");
            return entity;
        } catch (SQLException e) {
            logger.error("It is not possible to save the order object in the database!");
            throw new DaoException(e);
        }

    }

    @Override
    public List<Application> getPendingApplication() {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PENDING_APPLICATION)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applications.add(createApplication(resultSet));
            }
            logger.info("Application fond by ID");
            return applications;
        } catch (SQLException e) {
            logger.error("Failed to create list of pending applications");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processApplication(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_APPLICATION)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("Application processed");
        } catch (SQLException e) {
            logger.error("Invalid process application");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void statusApplication(Integer id, String status) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS)) {
            if ("true".equals(status)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
            logger.info("Status set successfully");
        } catch (SQLException e) {
            logger.error("Can't get status of application");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("Application deleted");
        } catch (SQLException e) {
            logger.error("Application deletion error");
            throw new RuntimeException(e);
        }
    }
}

