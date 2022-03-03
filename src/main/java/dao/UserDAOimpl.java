package dao;

import entity.user.Role;
import entity.user.User;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
public class UserDAOimpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String GET_BY_EMAIL_AND_PASS =
            "SELECT * FROM my_hotel.user WHERE email=? AND password=?";
    private static final String SAVE_USER =
            "INSERT INTO my_hotel.user (name, email, password, role) VALUES (?,?,?,?)";
    private static final String GET_BY_ID = "SELECT * FROM my_hotel.user WHERE id=?";

    @Override
    public Optional<User> findByEmailAndPass(String email, String password) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getObject("id", Integer.class))
                        .email(resultSet.getObject("email", String.class))
                        .password(resultSet.getObject("password", String.class))
                        .role(Role.valueOf(resultSet.getObject("role", String.class)))
                        .build();
            }
            logger.info("User fond successfully");
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error("Invalid user find ");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<String> getEmail(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getObject("email", String.class));
            }
            logger.info("E-mail fond by ID");
            return Optional.of("");
        } catch (SQLException e) {
            logger.error("Invalid find e-mail by ID");
            throw new DaoException(e);
        }
    }

    @Override
    public User save(User entity){
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {
            if (!findByEmailAndPass(entity.getName(), entity.getEmail()).equals(Optional.empty())) {
                logger.error("Error e-mail or password");
                throw new DaoException(entity);
            }
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getEmail());
            preparedStatement.setObject(3, entity.getPassword());
            preparedStatement.setObject(4, entity.getRole().name());
            preparedStatement.executeUpdate();
            logger.info("User saved");
            return entity;
        } catch (SQLException e) {
            logger.error("User save error");
            throw new DaoException(e);
        }
    }
}

