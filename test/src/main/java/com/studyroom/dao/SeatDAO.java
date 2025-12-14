package com.studyroom.dao;

import com.studyroom.model.Seat;
import java.util.List;

public interface SeatDAO {
    List<Seat> getSeatsByRoomId(int roomId);
    Seat getSeatById(int id);
    boolean updateSeatStatus(int id, String status);
}



