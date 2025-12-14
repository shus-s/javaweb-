package com.studyroom.dao;

import com.studyroom.model.StudyRoom;
import java.util.List;

public interface RoomDAO {
    List<StudyRoom> getAllRooms();
    StudyRoom getRoomById(int id);
    boolean addRoom(StudyRoom room);
    boolean updateRoom(StudyRoom room);
    boolean deleteRoom(int id);
}



