package dao;

import entity.user.Role;
import entity.user.User;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PasswordEncryptor;

import java.sql.*;
import java.util.Optional;

@NoArgsConstructor
public class UserDAOimpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String GET_BY_EMAIL_AND_PASS =
            "SELECT * FROM user WHERE email=? AND password=?";
    private static final String GET_BY_ID =
            "SELECT * FROM user WHERE id=?;";
    private static final String DELETE =
            "DELETE FROM user WHERE id=?;";
    private static final String SAVE_USER =
            "INSERT INTO user (name, email, password, role) VALUES (?,?,?,?)";

    @Override
    public Optional<User> findByEmailAndPass(String email, String password) {
        ResultSet resultSet = null;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASS)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, PasswordEncryptor.encryption(password));
            User user = null;
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .name(resultSet.getObject("name", String.class))
                        .email(resultSet.getObject("email", String.class))
                        .password(resultSet.getObject("password", String.class))
                        .role(Role.valueOf(resultSet.getObject("role", String.class)))
                        .build();
            }
            LOGGER.info("User fond successfully");
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            LOGGER.error("Invalid user find ");
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
    public Optional<String> getEmail(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getObject("email", String.class));
            }
            LOGGER.info("E-mail fond by ID");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Invalid find e-mail by ID");
            throw new DaoException(e);
        }
    }

    @Override
    public Integer save(User entity) {
        Integer id = 0;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            if (!findByEmailAndPass(entity.getName(), entity.getEmail()).equals(Optional.empty())) {
                LOGGER.error("Error e-mail or password");
                throw new DaoException(entity);
            }
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getEmail());
            preparedStatement.setObject(3, PasswordEncryptor.encryption(entity.getPassword()));
            preparedStatement.setObject(4, entity.getRole().name());
            preparedStatement.executeUpdate();
            LOGGER.info("User saved");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getObject(1, Integer.class);
                } else {
                    LOGGER.error("Creating user failed, no ID obtained.");
                }
                return id;
            }
        } catch (SQLException e) {
            LOGGER.error("User save error");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = User.builder()
                        .id(id)
                        .name(resultSet.getObject("name", String.class))
                        .email(resultSet.getObject("email", String.class))
                        .password(resultSet.getObject("password", String.class))
                        .role(Role.valueOf(resultSet.getObject("role", String.class)))
                        .build();
                return Optional.of(user);

            }
            LOGGER.info("User fond by ID");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Can't find user by ID");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("User deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("User delete error");
            throw new DaoException(e);
        }
    }
}

