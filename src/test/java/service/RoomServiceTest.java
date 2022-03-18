package service;

import dto.CheckoutDTO;
import dto.CreateRoomDTO;
import dto.RoomDTO;
import entity.room.Room;
import entity.room.Status;
import entity.room.Type;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class RoomServiceTest {
    RoomService roomService = new RoomService();
    CreateRoomDTO roomDTO = CreateRoomDTO.builder().id("99").bed("ONE").price("100").status("FREE").cleaning("TRUE").build();

    @Test
    public void findAll() {
        List<RoomDTO> list = roomService.findAll();
        Assert.assertEquals(list.size(), 16);
    }

    @Test
    public void findAllFreeById() {
        List<RoomDTO> list = roomService.findAllFreeById("100");
        for (RoomDTO room : list) {
            Assert.assertEquals(room.getStatus(), Status.FREE);
        }
    }

    @Test
    public void setBusy() {
        roomService.setBusy(11);
        Optional<RoomDTO> room = roomService.findRoomById(11);
        room.ifPresent(dto -> Assert.assertEquals(Status.BUSY, dto.getStatus()));
    }

    @Test
    public void setFree() {
        roomService.setFree(11);
        Optional<RoomDTO> room = roomService.findRoomById(11);
        room.ifPresent(dto -> Assert.assertEquals(Status.FREE, dto.getStatus()));
    }

    @Test
    public void setCleaning() {
        roomService.setCleaning(12);
        Optional<RoomDTO> room = roomService.findRoomById(12);
        room.ifPresent(dto -> Assert.assertEquals(Boolean.TRUE, dto.getCleaning()));
        roomService.setCleaned(12);
    }

    @Test
    public void setCleaned() {
        roomService.setCleaned(11);
        Optional<RoomDTO> room = roomService.findRoomById(11);
        room.ifPresent(dto -> Assert.assertEquals(Boolean.FALSE, dto.getCleaning()));
        roomService.setCleaning(11);
    }

    @Test
    public void findClientRoom() {

    }
}