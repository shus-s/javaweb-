<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>预约列表 - 自习室管理系统</title>
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
        <div class="card">
            <h2 class="card-title">预约列表</h2>
            
            <div style="margin-bottom: 20px;">
                <a href="${pageContext.request.contextPath}/reservation/add" class="btn btn-primary">新建预约</a>
            </div>
            
            <table class="table">
                <thead>
                    <tr>
                        <c:if test="${sessionScope.user.role == 'admin'}">
                            <th>用户</th>
                        </c:if>
                        <th>自习室</th>
                        <th>座位</th>
                        <th>预约日期</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reservation" items="${reservations}">
                        <tr>
                            <c:if test="${sessionScope.user.role == 'admin'}">
                                <td>${reservation.userName}</td>
                            </c:if>
                            <td>${reservation.roomName}</td>
                            <td>${reservation.seatNumber != null ? reservation.seatNumber : '未指定'}</td>
                            <td>${reservation.reservationDate}</td>
                            <td>${reservation.startTime}</td>
                            <td>${reservation.endTime}</td>
                            <td>
                                <span class="badge ${reservation.status == 'active' ? 'badge-success' : 'badge-danger'}">
                                    ${reservation.status == 'active' ? '有效' : '已取消'}
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/reservation/edit?id=${reservation.id}" class="btn btn-secondary btn-sm">编辑</a>
                                <a href="${pageContext.request.contextPath}/reservation/delete?id=${reservation.id}" 
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirm('确定要删除这个预约吗？')">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty reservations}">
                        <tr>
                            <td colspan="${sessionScope.user.role == 'admin' ? '8' : '7'}" style="text-align: center;">暂无预约记录</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>



