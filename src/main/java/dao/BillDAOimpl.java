package dao;

import entity.Bill;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class BillDAOimpl implements BillDAO {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String SAVE_BILL =
            "INSERT INTO bill (id, room, email, cost, paymentState) VALUE (?,?,?,?,?);";
    private static final String FIND_BILL =
            "SELECT * FROM bill;";
    private static final String UPDATE_BILL =
            "UPDATE bill SET paymentState=? WHERE id=?;";
    private static final String DELETE_BILL =
            "DELETE FROM bill WHERE id=?;";

    @Override
    public Optional<Bill> findById(Integer id) {
        Bill bill;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                bill = create(resultSet);
                if (bill.getId().equals(id)) {
                    return Optional.of(bill);
                }
            }
            LOGGER.info("Bill fond by ID");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Can't find bill by ID");
            throw new DaoException(e);
        }
    }

    @Override
    public List<Bill> findByEmail(String email) {
        ResultSet resultSet = null;
        List<Bill> bills = new ArrayList<>();
        Bill bill;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bill = create(resultSet);
                if (bill.getEmail().equals(email)) {
                    bills.add(bill);
                }
            }
            LOGGER.info("List of bills fond by e-mail");
            return bills;
        } catch (SQLException e) {
            LOGGER.error("Can't find bills by e-mail");
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
    public boolean isPaid(Integer id) {
        Optional<Bill> bill;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                bill = Optional.ofNullable(create(resultSet));
                if (bill.isPresent()) {
                    if (bill.get().getId().equals(id) && bill.get().getPaymentState()) {
                        LOGGER.info("The status of bill pay is true");
                        return true;
                    }
                }
            }
            LOGGER.info("The status of bill pay is false");
            return false;
        } catch (SQLException e) {
            LOGGER.error("Unable to find status bill");
            throw new DaoException(e);
        }
    }

    @Override
    public Integer save(Bill entity) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BILL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setObject(2, entity.getRoom());
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getCost());
            preparedStatement.setObject(5, entity.getPaymentState());
            preparedStatement.executeUpdate();
            LOGGER.info("Bill successfully saved");
            return entity.getId();
        } catch (SQLException e) {
            LOGGER.error("Invalid save bill");
            throw new DaoException(e);
        }
    }

    @Override
    public Bill create(ResultSet resultSet) {
        Bill bill;
        try {
            bill = Bill.builder()
                    .id(resultSet.getObject("id", Integer.class))
                    .room(resultSet.getObject("room", Integer.class))
                    .email(resultSet.getObject("email", String.class))
                    .cost(resultSet.getObject("cost", String.class))
                    .paymentState(resultSet.getObject("paymentState", Boolean.class))
                    .build();
            LOGGER.info("Bill successfully created");
            return bill;
        } catch (SQLException e) {
            LOGGER.error("Invalid create bill");
            throw new DaoException(e);
        }
    }

    @Override
    public void pay(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BILL)) {
            preparedStatement.setObject(1, true);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Bill paid successfully");
        } catch (SQLException e) {
            LOGGER.error("Bill payment error");
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BILL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Bill deleted successfully");
        } catch (SQLException e) {
            LOGGER.error("Bill delete error");
            throw new DaoException(e);
        }
    }

    @Override
    public Integer getRoomNumber(Integer id) {
        Optional<Bill> bill = findById(id);
        if (bill.isPresent()) {
            return bill.get().getRoom();
        }
        return 0;
    }
}
