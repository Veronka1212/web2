package dao;

import entity.room.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RoomDAO {
    List<Room> findAllFreeById(String id);

    Optional<Room> findRoomById(Integer id);

    Room create(ResultSet resultSet) throws SQLException;

    List<Room> findAll();

    void setBusy(Integer id);
}
