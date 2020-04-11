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

</head>
<body>

<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>
        <jsp:include page="static/popupbg.jsp"/>
        <div class="header-wrap">
            <div>
                <h2>Создание нового типа апартаментов</h2>
                <form:form modelAttribute="accommodation" method="post" action="${createURL}" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <lable for="arrival_date">arrival_date</lable>
                        <form:input path="arrival_date" type="date" cssClass="form-control" id="arrival_date"/>
                    </div>
                    <div class="form-group">
                        <lable for="departure_date">departure_date</lable>
                        <form:input path="departure_date" type="date" cssClass="form-control" id="departure_date"/>
                    </div>

                    <div class="form-group">
                        <select name="customer_id" id="customer_id" path="customer_id">
                            <c:forEach items="${customerList}" var="customerList">
                                <option value="${customerList.id}">
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
                        <select name="apartment_id" id="apartment_id" path="apartment_id">
                            <c:forEach items="${apartmentList}" var="apartmentList" varStatus="loop">
                                <option value="${apartmentList.id}">
                                    №${apartmentList.number},
                                        ${apartmentTypeList[loop.index].type};
                                        ${apartmentList.description};
                                        ${apartmentTypeList[loop.index].price};
                                    Комнат: ${apartmentTypeList[loop.index].rooms_number};
                                    Мест: ${apartmentTypeList[loop.index].places_number};
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/accommodation/");
</script>
</body>
