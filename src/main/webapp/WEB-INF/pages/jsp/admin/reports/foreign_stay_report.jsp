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
    <title>Отчет об иностранных клиентах</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/reports/foreign_stay_report"/>
    <spring:url value="${urlBase}${urlReturn}" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
</head>
<body>

<div class="content">
    <jsp:include page="../../static/top-menu.jsp"/>
    <jsp:include page="../../static/header.jsp"/>
    <main>
        <jsp:include page="../../static/popupbg.jsp"/>
        <div class="header-wrap">
            <div>
                <h1>Отчет об иностранных клиентах</h1>
                <h4>Укажите даты, для генерации отчета</h4>
                <form:form modelAttribute="reservation" method="post" action="${createURL}" cssClass="form"
                           id="reservation">
                    <div class="form-group">
                        <lable for="arrival_date">С</lable>
                        <form:input path="arrival_date" type="date" cssClass="form-control" id="arrival_date"/>
                    </div>
                    <div class="form-group">
                        <lable for="departure_date">По</lable>
                        <form:input path="departure_date" type="date" cssClass="form-control" id="departure_date"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Сгенерировать отчет</button>
                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../../static/footer.jsp"/>
<script>
    popup_active("/admin/reports/foreign_stay_report");
</script>
</body>
