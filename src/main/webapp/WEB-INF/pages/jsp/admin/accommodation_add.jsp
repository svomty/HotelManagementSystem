<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 11.04.2020
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
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
    <c:url var="urlReturn" value="/admin/accommodation/"/>
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
                    <input type="date" name="arrival_date_filter" id="arrival_date_filter"
                           value="${accommodation.arrival_date}">
                    <lable for="departure_date_filter">Дата выезда</lable>
                    <input type="date" name="departure_date_filter" id="departure_date_filter"
                           value="${accommodation.departure_date}">
                    <input name="page" id="page"
                           value="page" hidden>
                    <input name="size" id="size"
                           value="size" hidden>
                    <input name="sort" id="sort"
                           value="sort" hidden>
                    <input type="submit" value="Найти">
                    <span class="display-none btn-red" id="error_filter">Ошибка! Даты не могут быть одинаковыми!</span>
                </form>

                <form:form modelAttribute="accommodation" method="post" action="${createURL}" cssClass="form"
                           id="accommodation">
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

                    <div class="form-group">
                        <label for="customer_id">Клиент</label>

                        <a class="btn btn-outline-danger btn-sm" href="#" style="float: right"
                           onclick='toDefault("customer_id", "customer_id_filter")'>
                            Очистить
                        </a>

                        <input style="float: right" name="customer_id_filter" id="customer_id_filter"
                               onkeyup='filtering("customer_id", "customer_id_filter")'
                               placeholder="Поиск клиента..">
                    </div>
                    <div class="form-group">
                        <c:if test="${not empty full_name}">
                            <span class="btn-red" id="customer_filter">Выберите '${full_name}' или создайте нового клиента</span>
                        </c:if>
                        <select name="customer_id" id="customer_id" path="customer_id">
                            <c:forEach items="${customerList}" var="customerList">
                                <option value="${customerList.id}"
                                        <c:if test="${customerList.id == accommodation.customer_id}"> selected </c:if>>
                                        ${customerList.surname}
                                        ${customerList.name}
                                        ${customerList.patronymic},
                                        ${customerList.birth_date}.
                                    Паспортные данные: ${customerList.passport_serial_number}
                                </option>
                            </c:forEach>
                        </select>

                    </div>


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
                                        <c:if test="${apartmentList.id == accommodation.apartment_id}"> selected class="bold" </c:if>>
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

                    <form:form modelAttribute="identity" method="post" action="${createURL}"
                               cssClass="form">

                        <form:hidden path="identificator" id="identificator"/>

                        <form:form modelAttribute="checker" method="post" action="${createURL}"
                                   cssClass="form">

                            <div class="form-group">
                                <form:checkbox path="check" class="custom-control-input" id="check"/>
                                <label class="custom-control-label" for="check">Занять весь апартамент</label>
                                <form:checkbox path="check" id="check"/>
                            </div>

                            <form:form modelAttribute="view" method="post" action="${createURL}"
                                       cssClass="form">
                                <form:input path="page" class="custom-control-input" id="page"/>
                                <form:input path="size" class="custom-control-input" id="size"/>
                                <form:input path="sort" class="custom-control-input" id="sort"/>
                                <button type="submit" class="btn btn-primary">Сохранить</button>
                            </form:form>
                        </form:form>

                    </form:form>

                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/accommodation/");
    setDate("accommodation");
</script>
</body>
