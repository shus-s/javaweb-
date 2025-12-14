package com.studyroom.controller;

import com.studyroom.model.Reservation;
import com.studyroom.model.User;
import com.studyroom.service.ReservationService;
import com.studyroom.service.ReservationServiceImpl;
import com.studyroom.service.RoomService;
import com.studyroom.service.RoomServiceImpl;
import com.studyroom.service.SeatService;
import com.studyroom.service.SeatServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import com.studyroom.model.Seat;

@WebServlet("/reservation/*")
public class ReservationController extends HttpServlet {
    private ReservationService reservationService = new ReservationServiceImpl();
    private RoomService roomService = new RoomServiceImpl();
    private SeatService seatService = new SeatServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        // getSeats不需要登录验证，允许直接访问
        if ("/getSeats".equals(action)) {
            getSeatsByRoom(request, response);
            return;
        }

        // 其他操作需要登录验证
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        switch (action) {
            case "/list":
                listReservations(request, response, user);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteReservation(request, response);
                break;
            default:
                listReservations(request, response, user);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/add":
                addReservation(request, response, user);
                break;
            case "/update":
                updateReservation(request, response);
                break;
            default:
                listReservations(request, response, user);
                break;
        }
    }

    private void listReservations(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        List<Reservation> reservations;
        if ("admin".equals(user.getRole())) {
            reservations = reservationService.getAllReservations();
        } else {
            reservations = reservationService.getReservationsByUserId(user.getId());
        }
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/reservation_list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("rooms", roomService.getAllRooms());
        request.getRequestDispatcher("/reservation_add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            request.setAttribute("reservation", reservation);
            request.setAttribute("rooms", roomService.getAllRooms());
            if (reservation.getRoomId() > 0) {
                request.setAttribute("seats", seatService.getSeatsByRoomId(reservation.getRoomId()));
            }
            request.getRequestDispatcher("/reservation_edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservation/list");
        }
    }

    private void addReservation(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        Reservation reservation = new Reservation();
        reservation.setUserId(user.getId());
        reservation.setRoomId(Integer.parseInt(request.getParameter("room_id")));
        String seatIdStr = request.getParameter("seat_id");
        if (seatIdStr != null && !seatIdStr.isEmpty()) {
            reservation.setSeatId(Integer.parseInt(seatIdStr));
        }
        reservation.setReservationDate(Date.valueOf(request.getParameter("reservation_date")));
        reservation.setStartTime(Time.valueOf(request.getParameter("start_time") + ":00"));
        reservation.setEndTime(Time.valueOf(request.getParameter("end_time") + ":00"));
        reservation.setStatus("active");

        if (reservationService.addReservation(reservation)) {
            response.sendRedirect(request.getContextPath() + "/reservation/list");
        } else {
            request.setAttribute("error", "预约失败！可能时间冲突或座位已被占用。");
            request.setAttribute("rooms", roomService.getAllRooms());
            request.getRequestDispatcher("/reservation_add.jsp").forward(request, response);
        }
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Reservation reservation = new Reservation();
        reservation.setId(Integer.parseInt(request.getParameter("id")));
        reservation.setUserId(Integer.parseInt(request.getParameter("user_id")));
        reservation.setRoomId(Integer.parseInt(request.getParameter("room_id")));
        String seatIdStr = request.getParameter("seat_id");
        if (seatIdStr != null && !seatIdStr.isEmpty()) {
            reservation.setSeatId(Integer.parseInt(seatIdStr));
        }
        reservation.setReservationDate(Date.valueOf(request.getParameter("reservation_date")));
        reservation.setStartTime(Time.valueOf(request.getParameter("start_time") + ":00"));
        reservation.setEndTime(Time.valueOf(request.getParameter("end_time") + ":00"));
        reservation.setStatus(request.getParameter("status"));

        if (reservationService.updateReservation(reservation)) {
            response.sendRedirect(request.getContextPath() + "/reservation/list");
        } else {
            request.setAttribute("error", "更新失败！可能时间冲突或座位已被占用。");
            request.setAttribute("reservation", reservation);
            request.setAttribute("rooms", roomService.getAllRooms());
            request.getRequestDispatcher("/reservation_edit.jsp").forward(request, response);
        }
    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        reservationService.deleteReservation(id);
        response.sendRedirect(request.getContextPath() + "/reservation/list");
    }

    private void getSeatsByRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 允许未登录用户访问（可选，如果需要登录验证，可以取消注释下面的代码）
        /*
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"请先登录\"}");
            return;
        }
        */
        
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String roomIdStr = request.getParameter("room_id");
        if (roomIdStr == null || roomIdStr.isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        try {
            int roomId = Integer.parseInt(roomIdStr);
            List<Seat> seats = seatService.getSeatsByRoomId(roomId);
            
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < seats.size(); i++) {
                Seat seat = seats.get(i);
                if (i > 0) json.append(",");
                json.append("{");
                json.append("\"id\":").append(seat.getId()).append(",");
                // 转义特殊字符
                String seatNumber = seat.getSeatNumber() != null ? seat.getSeatNumber().replace("\\", "\\\\").replace("\"", "\\\"") : "";
                json.append("\"seatNumber\":\"").append(seatNumber).append("\",");
                String status = seat.getStatus() != null ? seat.getStatus().replace("\\", "\\\\").replace("\"", "\\\"") : "available";
                json.append("\"status\":\"").append(status).append("\"");
                json.append("}");
            }
            json.append("]");
            response.getWriter().write(json.toString());
        } catch (NumberFormatException e) {
            response.getWriter().write("[]");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String errorMsg = e.getMessage() != null ? e.getMessage().replace("\\", "\\\\").replace("\"", "\\\"") : "未知错误";
            response.getWriter().write("{\"error\":\"服务器错误: " + errorMsg + "\"}");
        }
    }
}



