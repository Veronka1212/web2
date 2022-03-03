package dao;

import entity.Bill;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class BillDAOimpl implements BillDAO {
    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

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
        Bill bill = Bill.builder().build();
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bill = create(resultSet);
                if (bill.getId().equals(id)) {
                    return Optional.of(bill);
                }
            }
            logger.info("Bill fond by ID");
            return Optional.ofNullable(bill);
        } catch (SQLException e) {
            logger.error("Can't find bill by ID");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bill> findByEmail(String email) {
        List<Bill> bills = new ArrayList<>();
        Bill bill;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bill = create(resultSet);
                if (bill.getEmail().equals(email)) {
                    bills.add(bill);
                }
            }
            logger.info("List of bills fond by e-mail");
            return bills;
        } catch (SQLException e) {
            logger.error("Can't find bills by e-mail");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPaied(Integer id) {
        Optional<Bill> bill;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BILL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bill = Optional.ofNullable(create(resultSet));
                if (bill.isPresent()) {
                    if (bill.get().getId().equals(id) && bill.get().getPaymentState()) {
                        logger.info("The status of bill pay is true");
                        return true;
                    }
                }
            }
            logger.info("The status of bill pay is false");
            return false;
        } catch (SQLException e) {
            logger.error("Unable to find status bill");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bill save(Bill entity) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_BILL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setObject(2, entity.getRoom());
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getCost());
            preparedStatement.setObject(5, entity.getPaymentState());
            preparedStatement.executeUpdate();
            logger.info("Bill successfully saved");
            return entity;
        } catch (SQLException e) {
            logger.error("Invalid save bill");
            throw new DaoException(e);
        }
    }

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
            logger.info("Bill successfully created");
            return bill;
        } catch (SQLException e) {
            logger.error("Invalid create bill");
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
            logger.info("Bill paid successfully");
        } catch (SQLException e) {
            logger.error("Bill payment error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BILL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.info("Bill deleted successfully");
        } catch (SQLException e) {
            logger.error("Bill delete error");
            throw new RuntimeException(e);
        }
    }

    public Integer getRoomNumber(Integer id) {
        return findById(id).get().getRoom();
    }
}
