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
    <c:url var="urlReturn" value="/admin/apartment/price/"/>
    <spring:url value="${urlBase}${urlReturn}add/" var="createURL"/>

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
                <h2>Создание нового типа апартаментов</h2>
                <form:form modelAttribute="apartmentType" method="post" action="${createURL}" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <lable for="price">Цена</lable>
                        <form:input path="price" cssClass="form-control" id="price"/>
                    </div>
                    <div class="form-group">
                        <lable for="rooms_number">Кол-во комнат</lable>
                        <form:input path="rooms_number" cssClass="form-control" id="rooms_number"/>
                    </div>
                    <div class="form-group">
                        <lable for="places_number">Кол-во мест</lable>
                        <form:input path="places_number" cssClass="form-control"/>
                    </div>

                    <form:radiobuttons path="type" id="type" items="${types}"/>

                    <div class="form-group">
                        <lable for="description">Описание</lable>
                        <form:input path="description" cssClass="form-control" id="description"/>
                    </div>

                    <form:form modelAttribute="myString" method="post" action="${createURL}"
                               cssClass="form">

                        <form:input path="myText" class="custom-control-input" id="myText"/>

                        <form:form modelAttribute="view" method="post" action="${createURL}"
                                   cssClass="form">
                            <form:input path="page" class="custom-control-input" id="page"/>
                            <form:input path="size" class="custom-control-input" id="size"/>
                            <form:input path="sort" class="custom-control-input" id="sort"/>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </form:form>

                    </form:form>

                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/apartment/price/");
</script>
</body>
</html>