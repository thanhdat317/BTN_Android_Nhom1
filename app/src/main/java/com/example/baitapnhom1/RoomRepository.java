package com.example.baitapnhom1;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private static RoomRepository instance;
    private List<Room> roomList;

    private RoomRepository() {
        roomList = new ArrayList<>();
        // Dữ liệu ban đầu (Hiệp)
        roomList.add(new Room("1", "Phòng 101", 1500000, false, "", ""));
        roomList.add(new Room("2", "Phòng 102", 2000000, true, "Nguyễn Văn A", "0987654321"));
    }

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    public List<Room> getAllRooms() {
        return roomList;
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void updateRoom(int index, Room room) {
        if (index >= 0 && index < roomList.size()) {
            roomList.set(index, room);
        }
    }

    public void deleteRoom(int index) {
        if (index >= 0 && index < roomList.size()) {
            roomList.remove(index);
        }
    }
}
