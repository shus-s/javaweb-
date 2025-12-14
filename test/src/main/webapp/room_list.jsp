<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>自习室列表 - 自习室管理系统</title>
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
            <h2 class="card-title">自习室列表</h2>
            
            <c:if test="${sessionScope.user.role == 'admin'}">
                <div style="margin-bottom: 20px;">
                    <a href="${pageContext.request.contextPath}/room/add" class="btn btn-success">添加自习室</a>
                </div>
            </c:if>
            
            <div class="room-grid">
                <c:forEach var="room" items="${rooms}">
                    <div class="room-card">
                        <h3>${room.roomName}</h3>
                        <p><strong>位置：</strong>${room.location}</p>
                        <p><strong>容量：</strong>${room.capacity}人</p>
                        <p><strong>描述：</strong>${room.description}</p>
                        <span class="badge ${room.status == 'available' ? 'badge-success' : 'badge-danger'}">
                            ${room.status == 'available' ? '可用' : '不可用'}
                        </span>
                        <div style="margin-top: 15px;">
                            <a href="${pageContext.request.contextPath}/room/detail?id=${room.id}" class="btn btn-primary btn-sm">查看详情</a>
                            <c:if test="${sessionScope.user.role == 'admin'}">
                                <a href="${pageContext.request.contextPath}/room/edit?id=${room.id}" class="btn btn-secondary btn-sm">编辑</a>
                                <a href="${pageContext.request.contextPath}/room/delete?id=${room.id}" 
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirm('确定要删除这个自习室吗？')">删除</a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>



