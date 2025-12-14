<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人中心 - 自习室管理系统</title>
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
            <h2 class="card-title">个人中心</h2>
            
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <c:if test="${sessionScope.user != null}">
                <form action="${pageContext.request.contextPath}/user/update" method="post">
                    <div class="form-group">
                        <label>用户名：</label>
                        <input type="text" class="form-control" value="${sessionScope.user.username}" disabled>
                    </div>
                    
                    <div class="form-group">
                        <label for="name">姓名：</label>
                        <input type="text" id="name" name="name" class="form-control" value="${sessionScope.user.name}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="student_id">学号：</label>
                        <input type="text" id="student_id" name="student_id" class="form-control" value="${sessionScope.user.studentId}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">手机号：</label>
                        <input type="text" id="phone" name="phone" class="form-control" value="${sessionScope.user.phone}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">邮箱：</label>
                        <input type="email" id="email" name="email" class="form-control" value="${sessionScope.user.email}" required>
                    </div>
                    
                    <div class="form-group">
                        <label>角色：</label>
                        <input type="text" class="form-control" value="${sessionScope.user.role == 'admin' ? '管理员' : '学生'}" disabled>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">更新信息</button>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>



