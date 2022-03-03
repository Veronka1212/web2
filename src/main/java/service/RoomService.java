package service;

import dao.RoomDAOimpl;
import dto.RoomDTO;
import entity.room.Status;
import lombok.NoArgsConstructor;
import mapper.RoomMapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public class RoomService {
    private final BillService billService = new BillService();
    private final RoomDAOimpl roomDAOmpl = new RoomDAOimpl();
    private final RoomMapper roomMapper = new RoomMapper();


    public List<RoomDTO> findAll() {
        return roomDAOmpl.findAll().stream()
                .map(roomMapper::getFrom)
                .collect(toList());
    }

    public List<RoomDTO> findAllFreeById(String id) {
        return roomDAOmpl.findAllFreeById(id).stream()
                .map(roomMapper::getFrom)
                .collect(toList());
    }

    public void setBusy(Integer id) {
        roomDAOmpl.setBusy(id);
    }

    public void setFree(Integer id) {
        roomDAOmpl.setFree(id);
    }

    public void setCleaning(Integer id) {
        roomDAOmpl.setCleaning(id);
    }

    public void setCleaned(Integer id) {
        roomDAOmpl.setCleaned(id);
    }

    public List<RoomDTO> findClientRoom(String email) {
        List<RoomDTO> clientRooms = new ArrayList<>();
        List<Integer> id = billService.getRoomNumbers(email);
        List<RoomDTO> rooms = findAll();
        for (RoomDTO room : rooms) {
            if (id.contains(room.getId()) && room.getStatus() == Status.BUSY) {
                clientRooms.add(room);
            }
        }
        return clientRooms;
    }
}
