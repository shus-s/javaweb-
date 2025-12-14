package com.studyroom.service;

import com.studyroom.dao.RoomDAO;
import com.studyroom.dao.RoomDAOImpl;
import com.studyroom.model.StudyRoom;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    private RoomDAO roomDAO = new RoomDAOImpl();

    @Override
    public List<StudyRoom> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    @Override
    public StudyRoom getRoomById(int id) {
        return roomDAO.getRoomById(id);
    }

    @Override
    public boolean addRoom(StudyRoom room) {
        if (room == null || room.getRoomName() == null || room.getRoomName().trim().isEmpty()) {
            return false;
        }
        return roomDAO.addRoom(room);
    }

    @Override
    public boolean updateRoom(StudyRoom room) {
        if (room == null || room.getId() <= 0) {
            return false;
        }
        return roomDAO.updateRoom(room);
    }

    @Override
    public boolean deleteRoom(int id) {
        if (id <= 0) {
            return false;
        }
        return roomDAO.deleteRoom(id);
    }
}



