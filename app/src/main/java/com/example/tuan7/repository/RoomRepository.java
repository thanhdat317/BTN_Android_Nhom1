package com.example.tuan7.repository;

import com.example.tuan7.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private static RoomRepository instance;
    private List<Room> roomList;

    private RoomRepository() {
        roomList = new ArrayList<>();
        // Dữ liệu mẫu
        roomList.add(new Room("1", "Phòng 101", 1500000, false, "", ""));
        roomList.add(new Room("2", "Phòng 102", 2000000, true, "Nguyễn Văn A", "0987654321"));
    }

    public static synchronized RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    // Create
    public void addRoom(Room room) {
        roomList.add(room);
    }

    // Read
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomList);
    }

    // Update
    public void updateRoom(Room updatedRoom) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(updatedRoom.getId())) {
                roomList.set(i, updatedRoom);
                return;
            }
        }
    }

    // Delete
    public void deleteRoom(String roomId) {
        roomList.removeIf(room -> room.getId().equals(roomId));
    }

    public Room getRoomById(String id) {
        for (Room room : roomList) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }
}
