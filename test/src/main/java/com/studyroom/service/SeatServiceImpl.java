package com.studyroom.service;

import com.studyroom.dao.SeatDAO;
import com.studyroom.dao.SeatDAOImpl;
import com.studyroom.model.Seat;
import java.util.List;

public class SeatServiceImpl implements SeatService {
    private SeatDAO seatDAO = new SeatDAOImpl();

    @Override
    public List<Seat> getSeatsByRoomId(int roomId) {
        return seatDAO.getSeatsByRoomId(roomId);
    }

    @Override
    public Seat getSeatById(int id) {
        return seatDAO.getSeatById(id);
    }

    @Override
    public boolean updateSeatStatus(int id, String status) {
        return seatDAO.updateSeatStatus(id, status);
    }
}



