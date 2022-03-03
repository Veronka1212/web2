package dao;

import command.ConstantsCommand;
import entity.Application;
import entity.room.Bed;
import entity.room.Room;
import entity.room.Status;
import entity.room.Type;
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

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class RoomDAOimpl implements RoomDAO {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

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
        try (Connection connection = BasicConnectionPool.connectPool().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FREE_ROOM);
            ResultSet resultSet = preparedStatement.executeQuery();
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
            logger.info("Rooms found");
            return rooms;
        } catch (SQLException e) {
            logger.error("Room search error");
            throw new RuntimeException(e);
        }
    }

    public Room create(ResultSet resultSet) {
        String cleaning;
        Room room;
        try {
            cleaning = resultSet.getObject("cleaning", String.class);
            if (cleaning.equals(ONE)) {
                cleaning = "true";
            }
            room = Room.builder().
                    id(resultSet.getObject("id", Integer.class)).
                    bed(Bed.valueOf(resultSet.getObject("bed", String.class))).
                    type(Type.valueOf(resultSet.getObject("type", String.class))).
                    price(resultSet.getObject("price", String.class)).
                    status(Status.valueOf(resultSet.getObject("status", String.class))).
                    cleaning(Boolean.valueOf(cleaning)).build();
            logger.info("Room creation successful");
            return room;
        } catch (SQLException e) {
            logger.error("Room creation error");
            throw new RuntimeException(e);
        }
    }

    public List<Room> findAll() {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(create(resultSet));
            }
            logger.info("Room search successful");
            return rooms;
        } catch (SQLException e) {
            logger.error("Room search error");
            throw new RuntimeException(e);
        }
    }

    public void setBusy(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_BUSY)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            logger.info("Successful to set status busy");
        } catch (SQLException e) {
            logger.error("Unable to set status busy");
            throw new RuntimeException(e);
        }
    }

    public void setCleaning(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_CLEANING)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            logger.info("Successful to set cleaning");
        } catch (SQLException e) {
            logger.error("Unable to set cleaning");
            throw new RuntimeException(e);
        }
    }

    public void setCleaned(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_CLEANED)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            logger.info("Successful to set cleaned");
        } catch (SQLException e) {
            logger.error("Unable to set cleaned");
            throw new RuntimeException(e);
        }
    }

    public void setFree(Integer id) {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_FREE)) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
            logger.info("Successful to set free");
        } catch (SQLException e) {
            logger.error("Unable to set free");
            throw new RuntimeException(e);
        }
    }
}
