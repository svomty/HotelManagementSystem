<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="popupbg" id="popupbg">
    <div class="answer-popup">
        <span>Панель администратора</span>
        <a href="${pageContext.request.contextPath}/admin/status">Статус гостиницы</a>
        <a href="${pageContext.request.contextPath}/admin/apartment/">Апартаменты</a>
        <a class="answer-popup__active" href="${pageContext.request.contextPath}/admin/apartment/price/list/">Цены</a>
        <a href="${pageContext.request.contextPath}/admin/reservation">Система онлайн бронирования</a>
        <a href="${pageContext.request.contextPath}/admin/client">Клиенты</a>
        <a href="${pageContext.request.contextPath}/admin/residence">Заселение</a>
        <a href="${pageContext.request.contextPath}/admin/config">Конфигурация главного меню</a>
        <span>Отчетность</span>
        <a href="${pageContext.request.contextPath}/admin/report/residence">Отчет о заселении</a>
        <a href="${pageContext.request.contextPath}/admin/report/profit">Отчет о доходности</a>
        <a href="${pageContext.request.contextPath}/admin/report/client">Отчет о клиентах</a>
    </div>
</div>
</body>
</html>
