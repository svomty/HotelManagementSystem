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
    <c:set var="arrival_date_filter" value="${arrival_date_filter}" scope="request"/>
    <c:set var="departure_date_filter" value="${departure_date_filter}" scope="request"/>

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

            <form action="${pageContext.request.contextPath}">
                <lable for="arrival_date_filter">Дата приезда</lable>
                <input type="date" name="arrival_date_filter" id="arrival_date_filter"
                       value="${accommodation.arrival_date}">
                <!--arrival_date_filter value="departure_date_filter"> -->
                <lable for="departure_date_filter">Дата выезда</lable>
                <input type="date" name="departure_date_filter" id="departure_date_filter"
                       value="${accommodation.departure_date}">
                <input type="submit" value="Найти">
                <span class="display-none btn-red" id="error_filter">Ошибка! Даты не могут быть одинаковыми!</span>
                <span class="display-none btn-red" id="error_filter2">Ошибка! Не все даты введены!</span>
            </form>


            <div id="reservation">
                <div style="display: flex; flex-wrap: nowrap">
                    <span>Свободных апартаментов: ${counterTotal}</span>
                    <jsp:include page="static/page_size.jsp"/>
                </div>

                <script>
                    setDate2("reservation");
                </script>
                <c:forEach items="${apartment_list}" var="apartment" varStatus="loop">

                    <div class="card">

                        <div class="container">

                            <div class="flex-container">
                                <div class="flex-item"><h2>${apartment.places_number }-местный ${apartment.type}</h2>
                                </div>
                                <div class="flex-item"><h3>Осталось номеров: ${counter[loop.index]}</h3></div>
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

                <jsp:include page="static/pagination.jsp"/>
            </div>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>