<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 24.03.2020
  Time: 2:36
  To change this template use File | Settings | File Templates.
--%>
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
    <c:url var="urlReturn" value="/admin/apartment/price/"/>
    <spring:url value="${urlBase}${urlReturn}add" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

    <c:set var="total_page" value="${total_page}" scope="request"/>
    <c:set var="current_page" value="${current_page}" scope="request"/>
    <c:set var="sort" value="${sort}" scope="request"/>
    <c:set var="size" value="${size}" scope="request"/>
    <c:set var="start_page" value="${start_page}" scope="request"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="/admin/apartment/price/" scope="request"/>
    <c:set var="type" value="${type}" scope="request"/>
    <c:set var="place" value="${place}" scope="request"/>
    <c:set var="room" value="${room}" scope="request"/>
</head>
<body>
<div class="content">
    <jsp:include page="../static/top-menu.jsp"/>
    <jsp:include page="../static/header.jsp"/>
    <main>
        <jsp:include page="../static/popupbg.jsp"/>
        <div class="header-wrap">
            <jsp:include page="../static/tableTopBar.jsp"/>
            <form style="display: none;" action="${urlBase}${urlReturn}" class="poisk-filter" method="get">
                <div style="display: flex; justify-content: right; width: 100%;">
                    <div>
                        <input name="page" id="page" value="1" hidden>
                        <input name="sort" id="sort" value="${sort}" hidden>
                        <input name="size" id="size" value="${size}" hidden>
                        <div style="display: flex; justify-content: right; width: 100%;">
                            <div>
                                <lable for="type">Тип апартамента</lable>
                                <select name="type">
                                    <option value="" ${"Простой".toLowerCase() != type.toLowerCase() || "Люкс".toLowerCase() != type.toLowerCase() ? 'selected="selected"' : ''}>
                                        Любой
                                    </option>
                                    <option value="Простой" ${"Простой".toLowerCase() == type.toLowerCase() ? 'selected="selected"' : ''}>
                                        Простой
                                    </option>
                                    <option value="Люкс" ${"Люкс".toLowerCase() == type.toLowerCase() ? 'selected="selected"' : ''}>
                                        Люкс
                                    </option>
                                </select>

                                <lable for="place">Количество мест</lable>
                                <input type="number" name="place" id="place" value="${place}">
                            </div>
                            <input style="width: 24px; height: 24px; padding-left: 1em;" type="image"
                                   src="${pageContext.request.contextPath}/img/poisk.png">

                            <a class="btn-discharge"
                               style="margin-left: 1em; padding-left: 5px; padding-right: 5px; padding-top: 2px;"
                               href="${urlBase}${urlReturn}">Сброс</a>
                        </div>
                        <div style="margin-top: 10px;">
                            <lable for="room">Количество комнат</lable>
                            <input type="number" name="room" id="room" value="${room}">
                        </div>
                    </div>
                </div>
            </form>

            <c:if test="${!empty apartment_list}">
                <div class="divTable" style="border: 1px solid #000;">
                    <div class="divTableHeading">
                        <div class="divTableRow">
                            <div class="divTableCell">
                                <a id="id"
                                   href="${pageContext.request.contextPath}?page=1&sort=id&size=${size}&type=${type}&place=${place}&room=${room}">id</a>
                            </div>
                            <div class="divTableCell">
                                <a id="price"
                                   href="${pageContext.request.contextPath}?page=1&sort=price&size=${size}&type=${type}&place=${place}&room=${room}">Стоимость
                                    места</a>
                            </div>
                            <div class="divTableCell">
                                <a id="rooms_number"
                                   href="${pageContext.request.contextPath}?page=1&sort=rooms_number&size=${size}&type=${type}&place=${place}&room=${room}">Кол-во
                                    комнат</a>
                            </div>
                            <div class="divTableCell">
                                <a id="places_number"
                                   href="${pageContext.request.contextPath}?page=1&sort=places_number&size=${size}&type=${type}&place=${place}&room=${room}">Кол-во
                                    мест</a>
                            </div>
                            <div class="divTableCell">
                                <a id="type"
                                   href="${pageContext.request.contextPath}?page=1&sort=type&size=${size}&type=${type}&place=${place}&room=${room}">Тип
                                    номера</a>
                            </div>
                            <div class="divTableCell">
                                <a id="description"
                                   href="${pageContext.request.contextPath}?page=1&sort=description&size=${size}&type=${type}&place=${place}&room=${room}">Описание</a>
                            </div>
                            <div class="divTableCell">Опции</div>
                        </div>
                    </div>
                    <c:forEach items="${apartment_list}" var="apartment">
                        <div class="divTableBody">
                            <div class="divTableRow">
                                <div class="divTableCell">${apartment.id}</div>
                                <div class="divTableCell">${apartment.price }</div>
                                <div class="divTableCell">${apartment.rooms_number}</div>
                                <div class="divTableCell">${apartment.places_number }</div>
                                <div class="divTableCell">${apartment.type }</div>
                                <div class="divTableCell">${apartment.description }</div>
                                <div class="divTableCell">
                                    <spring:url
                                            value="${urlBase}${urlReturn}update/${apartment.id }?page=${current_page}&size=${size}&sort=${sort}&type=${type}&place=${place}&room=${room}"
                                            var="updateURL"/>
                                    <a class="btn btn-primary" href="${updateURL }" role="button">
                                        <i style="color: #E2B231" class="fa fa-pencil-square" aria-hidden="true"></i>
                                    </a>
                                    <spring:url
                                            value="${urlBase}${urlReturn}delete/${apartment.id }?page=${current_page}&size=${size}&sort=${sort}&type=${type}&place=${place}&room=${room}"
                                            var="deleteURL"/>
                                    <a class="btn btn-primary" href="${deleteURL }" role="button">
                                        <i style="color: #E22C2F" class="fa fa-trash-o" aria-hidden="true"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <jsp:include page="../static/bottomBar.jsp"/>
            </c:if>
            <c:if test="${empty apartment_list}">

                <div style="display: block; flex-direction: column;">
                    <h1> Ничего не найдено :( </h1>
                    <a class="btn-discharge error" href="${urlBase}${urlReturn}">Сбросить фильтры</a>
                </div>

            </c:if>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
</body>
<script>
    btnActive("${sort}");
    popup_active("${pageContext.request.contextPath}/admin/apartment/price/");
</script>
</html>