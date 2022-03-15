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

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String FIND_ALL = "SELECT * FROM application;";
    private static final String SAVE_APPLICATION =
            "INSERT INTO application (email, bed, type, time, status, processing_status) VALUES (?,?,?,?,?,?) ";
    private static final String PENDING_APPLICATION =
            "SELECT * FROM application WHERE processing_status=0;";
    private static final String UPDATE_APPLICATION =
            "UPDATE application SET processing_status='1' WHERE id=?;";
    private static final String UPDATE_STATUS =
            "UPDATE application SET status=? WHERE id=?;";
    private static final String DELETE =
            "DELETE FROM application WHERE id=?;";


    @Override
    public List<Application> findAll() {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Application> applications = new ArrayList<>();
            while (resultSet.next()) {
                applications.add(createApplication(resultSet));
            }
            LOGGER.info("Return all applications");
            return applications;
        } catch (SQLException e) {
            LOGGER.error("Error creating list of all orders");
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
            LOGGER.info("Application object creation was successful");
            return application;
        } catch (SQLException e) {
            LOGGER.error("Error create application from data base");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Application> findByEmail(String email) {
        ResultSet resultSet = null;
        List<Application> applications = new ArrayList<>();
        Application application;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                application = createApplication(resultSet);
                if (application.getEmail().equals(email)) {
                    applications.add(application);
                }
            }
            LOGGER.info("Applications fond by email");
            return applications;
        } catch (SQLException e) {
            LOGGER.error("Error find by email");
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    @Override
    public Optional<Application> findById(String id) {
        Application application;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                application = createApplication(resultSet);
                if (application.getId().equals(Integer.parseInt(id))) {
                    return Optional.of(application);
                }
            }
            LOGGER.info("Application fond by ID");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Error find by ID");
            throw new DaoException(e);
        }
    }

    @Override
    public Integer save(Application entity) {
        Integer id = 0;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_APPLICATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getEmail());
            preparedStatement.setObject(2, entity.getBed().name());
            preparedStatement.setObject(3, entity.getType().name());
            preparedStatement.setObject(4, entity.getTime());
            preparedStatement.setObject(5, entity.getStatus());
            preparedStatement.setObject(6, entity.getProcessing_status());
            preparedStatement.executeUpdate();
            LOGGER.info("Application object saved in the database");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getObject(1,Integer.class);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
                return id;
            }
        } catch (SQLException e) {
            LOGGER.error("It is not possible to save the order object in the database!");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Application> getPendingApplication() {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PENDING_APPLICATION);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                applications.add(createApplication(resultSet));
            }
            LOGGER.info("Application fond by ID");
            return applications;
        } catch (SQLException e) {
            LOGGER.error("Failed to create list of pending applications");
            throw new DaoException(e);
        }
    }

    @Override
    public void processApplication(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_APPLICATION)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Application processed");
        } catch (SQLException e) {
            LOGGER.error("Invalid process application");
            throw new DaoException(e);
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
            LOGGER.info("Status set successfully");
        } catch (SQLException e) {
            LOGGER.error("Can't get status of application");
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Application deleted");
        } catch (SQLException e) {
            LOGGER.error("Application deletion error");
            throw new DaoException(e);
        }
    }
}

