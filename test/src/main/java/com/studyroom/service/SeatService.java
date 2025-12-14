package com.studyroom.service;

import com.studyroom.model.Seat;
import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByRoomId(int roomId);
    Seat getSeatById(int id);
    boolean updateSeatStatus(int id, String status);
}



