package com.studyroom.dao;

import com.studyroom.model.StudyRoom;
import com.studyroom.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public List<StudyRoom> getAllRooms() {
        List<StudyRoom> rooms = new ArrayList<>();
        String sql = "SELECT * FROM study_rooms ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public StudyRoom getRoomById(int id) {
        String sql = "SELECT * FROM study_rooms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRoom(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addRoom(StudyRoom room) {
        String sql = "INSERT INTO study_rooms (room_name, location, capacity, description, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomName());
            pstmt.setString(2, room.getLocation());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setString(4, room.getDescription());
            pstmt.setString(5, room.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRoom(StudyRoom room) {
        String sql = "UPDATE study_rooms SET room_name = ?, location = ?, capacity = ?, description = ?, status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomName());
            pstmt.setString(2, room.getLocation());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setString(4, room.getDescription());
            pstmt.setString(5, room.getStatus());
            pstmt.setInt(6, room.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM study_rooms WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private StudyRoom mapResultSetToRoom(ResultSet rs) throws SQLException {
        StudyRoom room = new StudyRoom();
        room.setId(rs.getInt("id"));
        room.setRoomName(rs.getString("room_name"));
        room.setLocation(rs.getString("location"));
        room.setCapacity(rs.getInt("capacity"));
        room.setDescription(rs.getString("description"));
        room.setStatus(rs.getString("status"));
        room.setCreatedAt(rs.getTimestamp("created_at"));
        return room;
    }
}



