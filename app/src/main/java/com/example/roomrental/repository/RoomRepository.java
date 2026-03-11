package com.example.roomrental.repository;

import com.example.roomrental.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private static RoomRepository instance;
    private List<Room> roomList;

    private RoomRepository() {
        roomList = new ArrayList<>();
        // Mock data
        roomList.add(new Room("R01", "Phòng 101", 1500000, true, "", ""));
        roomList.add(new Room("R02", "Phòng 102", 2000000, false, "Nguyen Van A", "0123456789"));
        roomList.add(new Room("R03", "Phòng 201", 1800000, true, "", ""));
    }

    public static synchronized RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    // CREATE
    public void addRoom(Room room) {
        roomList.add(room);
    }

    // READ
    public List<Room> getAllRooms() {
        return roomList;
    }

    // UPDATE
    public void updateRoom(int index, Room updatedRoom) {
        if (index >= 0 && index < roomList.size()) {
            roomList.set(index, updatedRoom);
        }
    }

    // DELETE
    public void deleteRoom(int index) {
        if (index >= 0 && index < roomList.size()) {
            roomList.remove(index);
        }
    }
}
