<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>自习室管理系统 - 首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand">自习室管理系统</a>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <li><a href="${pageContext.request.contextPath}/room/list">自习室列表</a></li>
     			        <li><a href="${pageContext.request.contextPath}/reservation/list">我的预约</a></li>
		               <li><a href="${pageContext.request.contextPath}/user/profile">个人中心</a></li>
     		            <li><a href="${pageContext.request.contextPath}/user/logout">退出</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/login.jsp">登录</a></li>
                        <li><a href="${pageContext.request.contextPath}/register.jsp">注册</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="hero">
            <h1>欢迎使用自习室管理系统</h1>
            <p>便捷的在线预约，让学习更高效</p>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="${pageContext.request.contextPath}/room/list" class="btn btn-primary">开始预约</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary">立即登录</a>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-secondary">免费注册</a>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="features">
            <div class="feature-card">
                <h3>📚 多自习室选择</h3>
                <p>提供多个自习室供您选择，满足不同学习需求</p>
            </div>
            <div class="feature-card">
                <h3>🪑 座位预约</h3>
                <p>在线选择座位，提前规划学习时间</p>
            </div>
            <div class="feature-card">
                <h3>⏰ 时间管理</h3>
                <p>灵活的时间段选择，合理安排学习计划</p>
            </div>
            <div class="feature-card">
                <h3>📱 便捷操作</h3>
                <p>简单易用的界面，快速完成预约操作</p>
            </div>
        </div>
    </div>
</body>
</html>



