package dao;

import entity.Checkout;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckoutDAOimpl implements CheckoutDAO {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String FIND_ALL =
            "SELECT * FROM checkout;";
    private static final String DELETE_CHECKOUT =
            "DELETE FROM checkout WHERE id=?;";
    private static final String SAVE_CHECKOUT =
            "INSERT INTO checkout (room) VALUE (?);";

    @Override
    public Checkout save(Checkout entity) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_CHECKOUT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getRoom());
            preparedStatement.executeUpdate();
            LOGGER.info("Checkout saved");
            return entity;
        } catch (SQLException e) {
            LOGGER.error("Invalid checkout saving");
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CHECKOUT)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Checkout deleted");
        } catch (SQLException e) {
            LOGGER.error("Checkout delete error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Checkout> findAll() {
        ResultSet resultSet = null;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            resultSet = preparedStatement.executeQuery();
            List<Checkout> checkouts = new ArrayList<>();
            while (resultSet.next()) {
                checkouts.add(createCheckout(resultSet));
            }
            LOGGER.info("Checkout list created");
            return checkouts;
        } catch (SQLException e) {
            LOGGER.error("Checkout list creation error");
            throw new RuntimeException(e);
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
    public Checkout createCheckout(ResultSet resultSet) {
        Checkout checkout;
        try {
            checkout = Checkout.builder().
                    id(resultSet.getObject("id", Integer.class)).
                    room(resultSet.getObject("room", Integer.class))
                    .build();
            LOGGER.info("Checkout created");
            return checkout;
        } catch (SQLException e) {
            LOGGER.error("Checkout create error");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Object> findById(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Checkout checkout = createCheckout(resultSet);
                if (checkout.getId().equals(id)) {
                    return Optional.of(checkout);
                }
            }
            LOGGER.info("Checkout fond by ID");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Can't find checkout by ID");
            throw new RuntimeException(e);
        }
    }
}
