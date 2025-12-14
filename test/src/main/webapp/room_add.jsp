<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加自习室 - 自习室管理系统</title>
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
            <h2 class="card-title">添加自习室</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/room/add" method="post" onsubmit="return validateRoomForm()">
                <div class="form-group">
                    <label for="room_name">自习室名称：</label>
                    <input type="text" id="room_name" name="room_name" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="location">位置：</label>
                    <input type="text" id="location" name="location" class="form-control" required>
                </div>
                
                <div class="form-group">
                    <label for="capacity">容量：</label>
                    <input type="number" id="capacity" name="capacity" class="form-control" min="1" required>
                </div>
                
                <div class="form-group">
                    <label for="description">描述：</label>
                    <textarea id="description" name="description" class="form-control" rows="4"></textarea>
                </div>
                
                <div class="form-group">
                    <label for="status">状态：</label>
                    <select id="status" name="status" class="form-control">
                        <option value="available">可用</option>
                        <option value="unavailable">不可用</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">添加</button>
                    <a href="${pageContext.request.contextPath}/room/list" class="btn btn-secondary">取消</a>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>



