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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>
    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/apartment/"/>
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
                <form:form modelAttribute="apartment" method="post" action="${createURL}" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <label for="number">Номер апартамента</label>
                        <input name="page" id="page"
                               value="page" hidden>
                        <input name="size" id="size"
                               value="size" hidden>
                        <input name="sort" id="sort"
                               value="sort" hidden>
                        <form:input path="number" cssClass="form-control" id="number"/>
                    </div>

                    <div class="form-group">
                        <label for="type_id">Тип апартамента</label>

                        <a class="btn btn-outline-danger btn-sm" href="#" style="float: right" onclick='toDefault("type_id", "type_id_filter")'>
                            Очистить
                        </a>
                        <input style="float: right" name="type_id_filter" id="type_id_filter" onkeyup='filtering("type_id", "type_id_filter")'
                               placeholder="Search type..">

                        <select name="type_id" id="type_id" path="type_id">
                            <c:forEach items="${apartmentType}" var="apartmentType">
                                <option value="${apartmentType.id}"
                                        <c:if test="${apartmentType.id == apartment.type_id}"> selected </c:if>>
                                        ${apartmentType.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <form:form modelAttribute="view" method="post" action="${createURL}"
                               cssClass="form">
                        <form:input path="page" class="custom-control-input" id="page"/>
                        <form:input path="size" class="custom-control-input" id="size"/>
                        <form:input path="sort" class="custom-control-input" id="sort"/>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </form:form>
                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
<script>
    popup_active("${pageContext.request.contextPath}/admin/apartment/");
    setView();
</script>
</body>
</html>