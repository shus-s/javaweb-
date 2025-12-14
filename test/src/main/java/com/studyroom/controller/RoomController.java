package com.studyroom.controller;

import com.studyroom.model.StudyRoom;
import com.studyroom.service.RoomService;
import com.studyroom.service.RoomServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/room/*")
public class RoomController extends HttpServlet {
    private RoomService roomService = new RoomServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/list":
                listRooms(request, response);
                break;
            case "/detail":
                showRoomDetail(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteRoom(request, response);
                break;
            default:
                listRooms(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/add":
                addRoom(request, response);
                break;
            case "/update":
                updateRoom(request, response);
                break;
            default:
                listRooms(request, response);
                break;
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<StudyRoom> rooms = roomService.getAllRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/room_list.jsp").forward(request, response);
    }

    private void showRoomDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        StudyRoom room = roomService.getRoomById(id);
        if (room != null) {
            request.setAttribute("room", room);
            request.getRequestDispatcher("/room_detail.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/room/list");
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/room_add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        StudyRoom room = roomService.getRoomById(id);
        if (room != null) {
            request.setAttribute("room", room);
            request.getRequestDispatcher("/room_edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/room/list");
        }
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StudyRoom room = new StudyRoom();
        room.setRoomName(request.getParameter("room_name"));
        room.setLocation(request.getParameter("location"));
        room.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        room.setDescription(request.getParameter("description"));
        room.setStatus(request.getParameter("status"));

        if (roomService.addRoom(room)) {
            response.sendRedirect(request.getContextPath() + "/room/list");
        } else {
            request.setAttribute("error", "添加失败！");
            request.getRequestDispatcher("/room_add.jsp").forward(request, response);
        }
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        StudyRoom room = new StudyRoom();
        room.setId(Integer.parseInt(request.getParameter("id")));
        room.setRoomName(request.getParameter("room_name"));
        room.setLocation(request.getParameter("location"));
        room.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        room.setDescription(request.getParameter("description"));
        room.setStatus(request.getParameter("status"));

        if (roomService.updateRoom(room)) {
            response.sendRedirect(request.getContextPath() + "/room/list");
        } else {
            request.setAttribute("error", "更新失败！");
            request.setAttribute("room", room);
            request.getRequestDispatcher("/room_edit.jsp").forward(request, response);
        }
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        roomService.deleteRoom(id);
        response.sendRedirect(request.getContextPath() + "/room/list");
    }
}



