<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>自习室详情 - 自习室管理系统</title>
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
            <h2 class="card-title">自习室详情</h2>
            
            <c:if test="${room != null}">
                <div class="form-group">
                    <label>自习室名称：</label>
                    <p>${room.roomName}</p>
                </div>
                
                <div class="form-group">
                    <label>位置：</label>
                    <p>${room.location}</p>
                </div>
                
                <div class="form-group">
                    <label>容量：</label>
                    <p>${room.capacity}人</p>
                </div>
                
                <div class="form-group">
                    <label>描述：</label>
                    <p>${room.description}</p>
                </div>
                
                <div class="form-group">
                    <label>状态：</label>
                    <span class="badge ${room.status == 'available' ? 'badge-success' : 'badge-danger'}">
                        ${room.status == 'available' ? '可用' : '不可用'}
                    </span>
                </div>
                
                <div style="margin-top: 20px;">
                    <c:if test="${room.status == 'available' && sessionScope.user != null}">
                        <a href="${pageContext.request.contextPath}/reservation/add?room_id=${room.id}" class="btn btn-primary">立即预约</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/room/list" class="btn btn-secondary">返回列表</a>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>



