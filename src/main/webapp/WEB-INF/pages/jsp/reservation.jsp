<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Цены на апартаменты</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/reservation/"/>
    <spring:url value="${urlBase}${urlReturn}add/" var="createURL"/>


    <c:set var="total_page" value="${total_page}" scope="request"/>
    <c:set var="current_page" value="${current_page}" scope="request"/>
    <c:set var="sort" value="${sort}" scope="request"/>
    <c:set var="size" value="${size}" scope="request"/>
    <c:set var="start_page" value="${start_page}" scope="request"/>
    <c:set var="createURL" value="${createURL}" scope="request"/>

</head>
<body>
<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>
        <div class="header-wrap">
            <jsp:include page="static/tableTopBar.jsp"/>

            <c:forEach items="${apartment_list}" var="apartment">

                <div class="card">

                    <div class="container">

                        <div class="flex-container">
                            <div class="flex-item"><h2>${apartment.places_number }-местный ${apartment.type}</h2></div>
                            <div class="flex-item"><h3>Осталось мест: ВЫСЧИТАТЬ</h3></div>
                        </div>
                        <div class="flex-container">
                            <div class="flex-item">
                                <h4><b>Количество комнат: </b> ${apartment.rooms_number}</h4>
                                <h4><b>Цена: </b> ${apartment.price} BYN</h4>
                                <h4><b>ОПЛАТА: </b> ПРИ ЗАСЕЛЕНИИ</h4>
                            </div>
                            <div class="flex-item">
                                <h4><b>Описание: </b></h4>
                                <h4>${apartment.description }</h4>
                            </div>
                        </div>
                        <div class="flex-container">
                            <div class="flex-item">
                                <img src="${pageContext.request.contextPath}/img/cards.jpg" alt="Avatar" width="100px">
                            </div>
                            <div class="flex-item">
                                <spring:url
                                        value="${urlBase}${urlReturn}${apartment.id }" var="reservationURL"/>
                                <a class="button7" href="${reservationURL }" role="button">
                                    Забронировать
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>

            <jsp:include page="static/bottomBar.jsp"/>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>
