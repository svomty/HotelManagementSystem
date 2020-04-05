<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 05.04.2020
  Time: 12:53
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

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/customer/"/>
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
                <h2>Добавление клиента</h2>
                <form:form modelAttribute="customer" method="post" action="${createURL}" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <lable for="surname">surname</lable>
                        <form:input path="surname" cssClass="form-control" id="surname"/>
                    </div>
                    <div class="form-group">
                        <lable for="name">name</lable>
                        <form:input path="name" cssClass="form-control" id="name"/>
                    </div>
                    <div class="form-group">
                        <lable for="patronymic">patronymic</lable>
                        <form:input path="patronymic" cssClass="form-control" id="patronymic"/>
                    </div>
                    <div class="form-group">
                        <lable for="birth_date">birth_date</lable>
                        <form:input path="birth_date" cssClass="form-control" id="birth_date"/>
                    </div>
                    <div class="form-group">
                        <lable for="passport_serial_number">passport_serial_number</lable>
                        <form:input path="passport_serial_number" cssClass="form-control" id="passport_serial_number"/>
                    </div>
                    <div class="form-group">
                        <lable for="identification_number">identification_number</lable>
                        <form:input path="identification_number" cssClass="form-control" id="identification_number"/>
                    </div>
                    <div class="form-group">
                        <lable for="date_issue_passport">date_issue_passport</lable>
                        <form:input path="date_issue_passport" cssClass="form-control" id="date_issue_passport"/>
                    </div>
                    <div class="form-group">
                        <lable for="issuing_authority">issuing_authority</lable>
                        <form:input path="issuing_authority" cssClass="form-control" id="issuing_authority"/>
                    </div>
                    <div class="form-group">
                        <lable for="registration_address">registration_address</lable>
                        <form:input path="registration_address" cssClass="form-control" id="registration_address"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>
</html>