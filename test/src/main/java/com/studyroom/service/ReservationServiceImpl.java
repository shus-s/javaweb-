package com.studyroom.service;

import com.studyroom.dao.ReservationDAO;
import com.studyroom.dao.ReservationDAOImpl;
import com.studyroom.model.Reservation;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private ReservationDAO reservationDAO = new ReservationDAOImpl();

    @Override
    public boolean addReservation(Reservation reservation) {
        if (reservation == null || reservation.getUserId() <= 0 || reservation.getRoomId() <= 0) {
            return false;
        }
        // 检查时间冲突
        if (reservation.getSeatId() != null) {
            if (checkTimeConflict(reservation.getRoomId(), reservation.getSeatId(), 
                    reservation.getReservationDate(), reservation.getStartTime(), 
                    reservation.getEndTime(), null)) {
                return false;
            }
        }
        return reservationDAO.addReservation(reservation);
    }

    @Override
    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    @Override
    public Reservation getReservationById(int id) {
        return reservationDAO.getReservationById(id);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() <= 0) {
            return false;
        }
        // 检查时间冲突（排除当前记录）
        if (reservation.getSeatId() != null) {
            if (checkTimeConflict(reservation.getRoomId(), reservation.getSeatId(), 
                    reservation.getReservationDate(), reservation.getStartTime(), 
                    reservation.getEndTime(), reservation.getId())) {
                return false;
            }
        }
        return reservationDAO.updateReservation(reservation);
    }

    @Override
    public boolean deleteReservation(int id) {
        if (id <= 0) {
            return false;
        }
        return reservationDAO.deleteReservation(id);
    }

    @Override
    public boolean checkTimeConflict(int roomId, int seatId, java.sql.Date date, java.sql.Time startTime, java.sql.Time endTime, Integer excludeId) {
        return reservationDAO.checkTimeConflict(roomId, seatId, date, startTime, endTime, excludeId);
    }
}
