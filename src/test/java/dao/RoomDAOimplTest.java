package dao;

import entity.Bill;
import entity.room.Bed;
import entity.room.Room;
import entity.room.Status;
import entity.room.Type;
import jdbc.BasicConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RoomDAOimplTest {

    RoomDAOimpl roomDAOimpl = new RoomDAOimpl();
    private static final String FIND_ROOM = "SELECT * FROM my_hotel.room WHERE id = 99 AND bed = 'ONE' AND type = 'ECONOMY' AND status = 'FREE' AND cleaning = true AND price = '100';";
    private static final String FIND_ROOM2 = "SELECT * FROM my_hotel.room WHERE id = 100 AND bed = 'ONE' AND type = 'ECONOMY' AND status = 'BUSY' AND cleaning = false AND price = '100';";

    @Test
    public void findAllFreeById() {
    }

    @Test
    public void create() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Room room = getRoom();
            if (resultSet.next()) {
                Assert.assertEquals(room, roomDAOimpl.create(resultSet));
            }
        }
    }

    @Test
    public void findAll() {
    }

    @Test
    public void setBusy() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM)) {
            roomDAOimpl.setBusy(99);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Room room = roomDAOimpl.create(resultSet);
                Assert.assertEquals("BUSY", room.getStatus());
            }
        }
        roomDAOimpl.setFree(99);
    }

    @Test
    public void setCleaned() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM)) {
            roomDAOimpl.setCleaned(99);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Room room = roomDAOimpl.create(resultSet);
                Assert.assertEquals(Boolean.FALSE, room.getCleaning());
            }
        }
        roomDAOimpl.setCleaning(99);
    }

    @Test
    public void setCleaning() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM2)) {
            roomDAOimpl.setCleaning(100);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Room room = roomDAOimpl.create(resultSet);
                Assert.assertEquals(Boolean.TRUE, room.getCleaning());
            }
        }
        roomDAOimpl.setCleaned(100);
    }

    @Test
    public void setFree() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM2)) {
            roomDAOimpl.setFree(100);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Room room = roomDAOimpl.create(resultSet);
                Assert.assertEquals("FREE", room.getStatus());
            }
        }
        roomDAOimpl.setBusy(100);
    }

    private Room getRoom() {
        Room room = Room.builder()
                .id(99)
                .bed(Bed.ONE)
                .type(Type.ECONOMY)
                .status(Status.FREE)
                .cleaning(Boolean.TRUE)
                .price("100")
                .build();
        return room;
    }
}