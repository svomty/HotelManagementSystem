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
        <a id="/admin/status" href="${pageContext.request.contextPath}/admin/status">Статус гостиницы</a>
        <a id="/admin/apartment/" href="${pageContext.request.contextPath}/admin/apartment/">Апартаменты</a>
        <a id="/admin/apartment/price/" href="${pageContext.request.contextPath}/admin/apartment/price/list/">Цены</a>
        <a id="/admin/reservation" href="${pageContext.request.contextPath}/admin/reservation">Система онлайн бронирования</a>
        <a id="/admin/client" href="${pageContext.request.contextPath}/admin/client">Клиенты</a>
        <a id="/admin/residence" href="${pageContext.request.contextPath}/admin/residence">Заселение</a>
        <a id="/admin/config" href="${pageContext.request.contextPath}/admin/config">Конфигурация главного меню</a>
        <span>Отчетность</span>
        <a id="/admin/report/residence" href="${pageContext.request.contextPath}/admin/report/residence">Отчет о заселении</a>
        <a id="/admin/report/profit" href="${pageContext.request.contextPath}/admin/report/profit">Отчет о доходности</a>
        <a id="/admin/report/client" href="${pageContext.request.contextPath}/admin/report/client">Отчет о клиентах</a>
    </div>
</div>
</body>
<script>
    popup_active("${pageContext.request.contextPath}");
</script>
</html>
