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
    <title>Добавление новой записи</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/reservation/"/>
    <spring:url value="${urlBase}${urlReturn}add/" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="${createURL}" scope="request"/>

    <c:set var="arrival_date_filter" value="${arrival_date_filter}" scope="request"/>
    <c:set var="departure_date_filter" value="${departure_date_filter}" scope="request"/>
</head>
<body>

<div class="content">
    <jsp:include page="../static/top-menu.jsp"/>
    <jsp:include page="../static/header.jsp"/>
    <main>
        <jsp:include page="../static/popupbg.jsp"/>
        <div class="header-wrap">
            <div>
                <h2>Добавление информации о заселении</h2>

                <form action="${pageContext.request.contextPath}">
                    <lable for="arrival_date_filter">Дата приезда</lable>
                    <input type="date" name="arrival_date_filter" id="arrival_date_filter" value="arrival_date_filter">
                    <lable for="departure_date_filter">Дата выезда</lable>
                    <input type="date" name="departure_date_filter" id="departure_date_filter"
                           value="departure_date_filter">
                    <input type="submit" value="Найти">
                    <span class="display-none btn-red" id="error_filter">Ошибка! Даты не могут быть одинаковыми!</span>
                </form>

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
                        <form:input path="departure_date" type="date" cssClass="form-control" id="departure_date"
                                    readonly="true"/>
                    </div>

                    <lable for="full_name">ФИО</lable>
                    <form:input path="full_name" cssClass="form-control" id="full_name"/>

                    <lable for="customer_phone">Телефон для связи</lable>
                    <form:input path="customer_phone" cssClass="form-control" id="customer_phone"/>

                    <div class="form-group">
                        <label for="apartment_id">Апартамент</label>

                        <a class="btn btn-outline-danger btn-sm" href="#" style="float: right"
                           onclick='toDefault("apartment_id", "apartment_id_filter")'>
                            Очистить
                        </a>

                        <input style="float: right" name="apartment_id_filter" id="apartment_id_filter"
                               onkeyup='filtering("apartment_id", "apartment_id_filter")'
                               placeholder="Поиск апартамента..">
                    </div>
                    <div class="form-group">
                        <select name="apartment_id" id="apartment_id" path="apartment_id">
                            <c:forEach items="${apartmentList}" var="apartmentList" varStatus="loop">
                                <option value="${apartmentList.id}"
                                        <c:if test="${apartmentList.id == accommodation.apartment_id}"> selected </c:if>>
                                    №${apartmentList.number},
                                        ${apartmentTypeList[loop.index].type};
                                        ${apartmentTypeList[loop.index].price};
                                    Комнат: ${apartmentTypeList[loop.index].rooms_number};
                                    <c:if test="${apartmentTypeList[loop.index].places_number == totalPlaces[loop.index]}">
                                        Мест: ${apartmentTypeList[loop.index].places_number};
                                        ПОЛНОСТЬЮ СВОБОДЕН;
                                    </c:if>
                                    <c:if test="${apartmentTypeList[loop.index].places_number != totalPlaces[loop.index]}">
                                        Свободно мест: ${apartmentTypeList[loop.index].places_number};
                                        Всего: ${totalPlaces[loop.index]};
                                    </c:if>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <lable for="arrived">Подтверждено</lable>
                    <form:input path="arrived" cssClass="form-control" id="arrived"/>

                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/reservation/");
    setDate("reservation");
</script>
</body>
