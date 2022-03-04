package dao;

import entity.Checkout;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDAOimpl implements CheckoutDAO {
    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

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
            logger.info("Checkout saved");
            return entity;
        } catch (SQLException e) {
            logger.error("Invalid checkout saving");
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CHECKOUT)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("Checkout deleted");
        } catch (SQLException e) {
            logger.error("Checkout delete error");
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
            logger.info("Checkout list created");
            return checkouts;
        } catch (SQLException e) {
            logger.error("Checkout list creation error");
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
            logger.info("Checkout created");
            return checkout;
        } catch (SQLException e) {
            logger.error("Checkout create error");
            throw new DaoException(e);
        }
    }
}
