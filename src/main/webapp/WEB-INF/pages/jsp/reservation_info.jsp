<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Цены на апартаменты</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">

</head>
<body>
<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>
        <div class="header-wrap">
            <div class="flex-wrap">
                <div>Дата приезда: ${reservation.arrival_date}</div>
                <div>Дата выезда: ${reservation.departure_date}</div>
                <div>ID бронирования: ${reservation.id}</div>
                <div>Ваше ФИО: ${reservation.full_name}</div>
                <div>Ваш телефон: ${reservation.customer_phone}</div>
                <div>Номер апартамента, в который Вас заселят — ${reservation.apartment_id}</div>
            </div>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>