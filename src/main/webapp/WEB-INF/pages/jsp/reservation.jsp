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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/modalWindow.css" rel="stylesheet">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.5.0.min.js"></script>

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/reservation/"/>
    <spring:url value="${urlBase}${urlReturn}" var="createURL"/>
    <c:set var="arrival_date_filter" value="${arrival_date_filter}" scope="request"/>
    <c:set var="departure_date_filter" value="${departure_date_filter}" scope="request"/>

    <c:set var="total_page" value="${total_page}" scope="request"/>
    <c:set var="current_page" value="${current_page}" scope="request"/>
    <c:set var="sort" value="${sort}" scope="request"/>
    <c:set var="size" value="${size}" scope="request"/>
    <c:set var="start_page" value="${start_page}" scope="request"/>
    <c:set var="createURL" value="${createURL}" scope="request"/>
    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="config" value="${config}" scope="request"/>
</head>
<body>
<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>

        <div id="modal-container">
            <div class="modal-background">
                <div class="modal">
                    <div class="close">
                        <i class="fa fa-times-circle-o" aria-hidden="true"></i>
                    </div>
                    <div class="block">
                        <form:form modelAttribute="reservation" method="post" action="${createURL}" cssClass="form"
                                   id="reservation">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <lable for="arrival_date">Введенная дата приезда</lable>
                                <form:input path="arrival_date" type="date" cssClass="form-control" id="arrival_date"
                                            readonly="true"/>
                            </div>
                            <div class="form-group">
                                <lable for="departure_date">Введенная дата выезда</lable>
                                <form:input path="departure_date" type="date" cssClass="form-control"
                                            id="departure_date"
                                            readonly="true"/>
                            </div>

                            <lable for="full_name">ФИО</lable>
                            <form:input path="full_name" cssClass="form-control" id="full_name"/>

                            <lable for="customer_phone">Телефон для связи</lable>
                            <form:input path="customer_phone" cssClass="form-control" id="customer_phone"/>

                            <form:hidden path="apartment_id" cssClass="form-control" id="apartment_id" readonly="true"/>

                            <button type="submit" class="btn">Забронировать</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <div class="header-wrap">

            <form action="${pageContext.request.contextPath}">
                <lable for="arrival_date_filter">Дата приезда</lable>
                <input type="date" name="arrival_date_filter" id="arrival_date_filter"
                       value="${accommodation.arrival_date}">
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
                                    <img src="${pageContext.request.contextPath}/img/cards.jpg" alt="Avatar"
                                         width="100px">
                                </div>
                                <div class="flex-item">
                                    <spring:url
                                            value="${urlBase}${urlReturn}${apartment.id }" var="reservationURL"/>
                                    <a id="one" class="button7 button" name="${apartment.id}" href="#" role="button">
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
<script src="${pageContext.request.contextPath}/js/modalWindow.js"></script>