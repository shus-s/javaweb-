package com.studyroom.service;

import com.studyroom.model.Reservation;
import java.util.List;

public interface ReservationService {
    boolean addReservation(Reservation reservation);
    List<Reservation> getReservationsByUserId(int userId);
    List<Reservation> getAllReservations();
    Reservation getReservationById(int id);
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(int id);
    boolean checkTimeConflict(int roomId, int seatId, java.sql.Date date, java.sql.Time startTime, java.sql.Time endTime, Integer excludeId);
}



