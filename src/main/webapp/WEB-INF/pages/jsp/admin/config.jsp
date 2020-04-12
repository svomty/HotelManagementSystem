<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 12.04.2020
  Time: 15:21
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
    <title>Настройка конфигурации сайта</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/config/"/>
    <spring:url value="${urlBase}${urlReturn}" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="${createURL}" scope="request"/>
</head>
<body>

<div class="content">
    <jsp:include page="../static/top-menu.jsp"/>
    <jsp:include page="../static/header.jsp"/>
    <main>
        <jsp:include page="../static/popupbg.jsp"/>
        <div class="header-wrap">
            <div>
                <h2>Настройка конфигурации сайта</h2>

                <form:form modelAttribute="config" method="post" action="${createURL}" cssClass="form">

                    <lable for="phone">Телефон</lable>
                    <form:input path="phone" cssClass="form-control" id="phone"/>

                    <lable for="email">Email</lable>
                    <form:input path="email" cssClass="form-control" id="email"/>

                    <lable for="address">Адрес</lable>
                    <form:input path="address" cssClass="form-control" id="address"/>

                    <lable for="name">Телефон</lable>
                    <form:input path="name" cssClass="form-control" id="name"/>

                    <lable for="countElem">Количество элементов на странице</lable>
                    <form:input path="countElem" cssClass="form-control" id="countElem"/>

                    <lable for="login">Логин</lable>
                    <form:input path="login" cssClass="form-control" id="login"/>

                    <lable for="password">Пароль</lable>
                    <form:input path="password" type="password" cssClass="form-control" id="password"/>

                    <input type="submit" value="Сохранить">
                </form:form>

            </div>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/config/");
</script>
</body>
</html>