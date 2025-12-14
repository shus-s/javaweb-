package com.studyroom.dao;

import com.studyroom.model.Reservation;
import com.studyroom.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (user_id, room_id, seat_id, reservation_date, start_time, end_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getUserId());
            pstmt.setInt(2, reservation.getRoomId());
            if (reservation.getSeatId() != null) {
                pstmt.setInt(3, reservation.getSeatId());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            pstmt.setDate(4, reservation.getReservationDate());
            pstmt.setTime(5, reservation.getStartTime());
            pstmt.setTime(6, reservation.getEndTime());
            pstmt.setString(7, reservation.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, u.name as user_name, sr.room_name, s.seat_number " +
                     "FROM reservations r " +
                     "LEFT JOIN users u ON r.user_id = u.id " +
                     "LEFT JOIN study_rooms sr ON r.room_id = sr.id " +
                     "LEFT JOIN seats s ON r.seat_id = s.id " +
                     "WHERE r.user_id = ? ORDER BY r.reservation_date DESC, r.start_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, u.name as user_name, sr.room_name, s.seat_number " +
                     "FROM reservations r " +
                     "LEFT JOIN users u ON r.user_id = u.id " +
                     "LEFT JOIN study_rooms sr ON r.room_id = sr.id " +
                     "LEFT JOIN seats s ON r.seat_id = s.id " +
                     "ORDER BY r.reservation_date DESC, r.start_time DESC";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservation getReservationById(int id) {
        String sql = "SELECT r.*, u.name as user_name, sr.room_name, s.seat_number " +
                     "FROM reservations r " +
                     "LEFT JOIN users u ON r.user_id = u.id " +
                     "LEFT JOIN study_rooms sr ON r.room_id = sr.id " +
                     "LEFT JOIN seats s ON r.seat_id = s.id " +
                     "WHERE r.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE reservations SET room_id = ?, seat_id = ?, reservation_date = ?, start_time = ?, end_time = ?, status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getRoomId());
            if (reservation.getSeatId() != null) {
                pstmt.setInt(2, reservation.getSeatId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setDate(3, reservation.getReservationDate());
            pstmt.setTime(4, reservation.getStartTime());
            pstmt.setTime(5, reservation.getEndTime());
            pstmt.setString(6, reservation.getStatus());
            pstmt.setInt(7, reservation.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReservation(int id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkTimeConflict(int roomId, int seatId, java.sql.Date date, java.sql.Time startTime, java.sql.Time endTime, Integer excludeId) {
        // 检查时间冲突：新预约的开始时间在已有预约的时间段内，或新预约的结束时间在已有预约的时间段内
        // 或者新预约完全包含已有预约
        String sql = "SELECT COUNT(*) FROM reservations WHERE room_id = ? AND seat_id = ? AND reservation_date = ? " +
                     "AND status = 'active' AND NOT (end_time <= ? OR start_time >= ?)";
        if (excludeId != null) {
            sql += " AND id != ?";
        }
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, seatId);
            pstmt.setDate(3, date);
            pstmt.setTime(4, startTime);
            pstmt.setTime(5, endTime);
            if (excludeId != null) {
                pstmt.setInt(6, excludeId);
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setUserId(rs.getInt("user_id"));
        reservation.setRoomId(rs.getInt("room_id"));
        int seatId = rs.getInt("seat_id");
        if (!rs.wasNull()) {
            reservation.setSeatId(seatId);
        }
        reservation.setReservationDate(rs.getDate("reservation_date"));
        reservation.setStartTime(rs.getTime("start_time"));
        reservation.setEndTime(rs.getTime("end_time"));
        reservation.setStatus(rs.getString("status"));
        reservation.setCreatedAt(rs.getTimestamp("created_at"));
        
        // 关联信息
        if (rs.getString("user_name") != null) {
            reservation.setUserName(rs.getString("user_name"));
        }
        if (rs.getString("room_name") != null) {
            reservation.setRoomName(rs.getString("room_name"));
        }
        if (rs.getString("seat_number") != null) {
            reservation.setSeatNumber(rs.getString("seat_number"));
        }
        
        return reservation;
    }
}

