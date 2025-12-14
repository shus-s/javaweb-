<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑预约 - 自习室管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand">自习室管理系统</a>
            <ul class="navbar-nav">
                <li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
                <li><a href="${pageContext.request.contextPath}/room/list">自习室列表</a></li>
                <li><a href="${pageContext.request.contextPath}/reservation/list">我的预约</a></li>
                <li><a href="${pageContext.request.contextPath}/user/profile">个人中心</a></li>
                <li><a href="${pageContext.request.contextPath}/user/logout">退出</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="card" style="max-width: 600px; margin: 30px auto;">
            <h2 class="card-title">编辑预约</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <c:if test="${reservation != null}">
                <form action="${pageContext.request.contextPath}/reservation/update" method="post" onsubmit="return validateReservationForm()">
                    <input type="hidden" name="id" value="${reservation.id}">
                    <input type="hidden" name="user_id" value="${reservation.userId}">
                    
                    <div class="form-group">
                        <label for="room_id">自习室：</label>
                        <select id="room_id" name="room_id" class="form-control" required onchange="loadSeats(this.value)">
                            <c:forEach var="room" items="${rooms}">
                                <option value="${room.id}" ${reservation.roomId == room.id ? 'selected' : ''}>${room.roomName} - ${room.location}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="seat_id">座位（可选）：</label>
                        <select id="seat_id" name="seat_id" class="form-control">
                            <option value="">不指定座位</option>
                            <c:forEach var="seat" items="${seats}">
                                <option value="${seat.id}" ${reservation.seatId == seat.id ? 'selected' : ''}>${seat.seatNumber}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="reservation_date">预约日期：</label>
                        <input type="date" id="reservation_date" name="reservation_date" class="form-control" 
                               value="<fmt:formatDate value='${reservation.reservationDate}' pattern='yyyy-MM-dd'/>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="start_time">开始时间：</label>
                        <input type="time" id="start_time" name="start_time" class="form-control" 
                               value="<fmt:formatDate value='${reservation.startTime}' pattern='HH:mm'/>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="end_time">结束时间：</label>
                        <input type="time" id="end_time" name="end_time" class="form-control" 
                               value="<fmt:formatDate value='${reservation.endTime}' pattern='HH:mm'/>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">状态：</label>
                        <select id="status" name="status" class="form-control">
                            <option value="active" ${reservation.status == 'active' ? 'selected' : ''}>有效</option>
                            <option value="cancelled" ${reservation.status == 'cancelled' ? 'selected' : ''}>已取消</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">更新预约</button>
                        <a href="${pageContext.request.contextPath}/reservation/list" class="btn btn-secondary">取消</a>
                    </div>
                </form>
            </c:if>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
    <script>
        // 页面加载时，如果已选择自习室，加载座位
        window.onload = function() {
            var roomId = document.getElementById("room_id").value;
            if (roomId) {
                loadSeats(roomId);
            }
        };
    </script>
</body>
</html>



