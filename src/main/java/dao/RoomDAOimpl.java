package dao;

import entity.Application;
import entity.room.Bed;
import entity.room.Room;
import entity.room.Status;
import entity.room.Type;
import exeption.DaoException;
import jdbc.BasicConnectionPool;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class RoomDAOimpl implements RoomDAO {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    private static final String GET_FREE_ROOM =
            "SELECT * FROM room WHERE status='FREE';";
    private static final String GET_ALL =
            "SELECT * FROM room;";
    private static final String SET_BUSY =
            "UPDATE room SET status='BUSY' WHERE id=?;";
    private static final String SET_FREE =
            "UPDATE room SET status='FREE' WHERE id=?;";
    private static final String SET_CLEANING =
            "UPDATE room SET cleaning = true WHERE id=?;";
    private static final String SET_CLEANED =
            "UPDATE room SET cleaning = false WHERE id=?;";

    public List<Room> findAllFreeById(String id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FREE_ROOM);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ApplicationDAOimpl applicationDAOimpl = new ApplicationDAOimpl();
            Application application = Application.builder().build();
            if (applicationDAOimpl.findById(id).isPresent()) {
                application = applicationDAOimpl.findById(id).get();
            }
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                Room room = create(resultSet);
                if (application.getBed().equals(room.getBed()) && application.getType().equals(room.getType())) {
                    rooms.add(room);
                }
            }
            LOGGER.info("Rooms found");
            return rooms;
        } catch (SQLException e) {
            LOGGER.error("Room search error");
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Room> findRoomById(Integer id) {
        Room room;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                room = create(resultSet);
                if (id.equals(room.getId())) {
                    return Optional.of(room);
                }
            }
            LOGGER.info("Room found");
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Room search error");
            throw new DaoException(e);
        }
    }

    public Room create(ResultSet resultSet) {
        Room room;
        try {
            room = Room.builder().
                    id(resultSet.getObject("id", Integer.class)).
                    bed(Bed.valueOf(resultSet.getObject("bed", String.class))).
                    type(Type.valueOf(resultSet.getObject("type", String.class))).
                    price(resultSet.getObject("price", String.class)).
                    status(Status.valueOf(resultSet.getObject("status", String.class))).
                    cleaning(resultSet.getObject("cleaning", Boolean.class)).build();
            LOGGER.info("Room creation successful");
            return room;
        } catch (SQLException e) {
            LOGGER.error("Room creation error");
            throw new DaoException(e);
        }
    }

    public List<Room> findAll() {
        ResultSet resultSet = null;
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(create(resultSet));
            }
            LOGGER.info("Room search successful");
            return rooms;
        } catch (SQLException e) {
            LOGGER.error("Room search error");
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

    public void setBusy(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_BUSY)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Successful to set status busy");
        } catch (SQLException e) {
            LOGGER.error("Unable to set status busy");
            throw new DaoException(e);
        }
    }

    public void setCleaning(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_CLEANING)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Successful to set cleaning");
        } catch (SQLException e) {
            LOGGER.error("Unable to set cleaning");
            throw new DaoException(e);
        }
    }

    public void setCleaned(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_CLEANED)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Successful to set cleaned");
        } catch (SQLException e) {
            LOGGER.error("Unable to set cleaned");
            throw new DaoException(e);
        }
    }

    public void setFree(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_FREE)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Successful to set free");
        } catch (SQLException e) {
            LOGGER.error("Unable to set free");
            throw new DaoException(e);
        }
    }
}
