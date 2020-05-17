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
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>
</head>
<body>
<div class="popupbg" id="popupbg">
    <div class="answer-popup">
        <span>Панель администратора</span>
        <a id="/admin/apartment/" href="${pageContext.request.contextPath}/admin/apartment/">Апартаменты</a>
        <a id="/admin/apartment/price/" href="${pageContext.request.contextPath}/admin/apartment/price/list/">Цены</a>
        <a id="/admin/reservation/" href="${pageContext.request.contextPath}/admin/reservation/">Система онлайн
            бронирования</a>
        <a id="/admin/customer/" href="${pageContext.request.contextPath}/admin/customer/">Клиенты</a>
        <a id="/admin/accommodation/" href="${pageContext.request.contextPath}/admin/accommodation/">Заселение</a>
        <a id="/admin/config/" href="${pageContext.request.contextPath}/admin/config/">Конфигурация главного меню</a>
    </div>
</div>
</body>
<script>
    popup_active("${pageContext.request.contextPath}");
</script>
</html>
