<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Произошла ошибка</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
</head>
<body>
<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>

    <main>
        <div class="header-wrap">

            <div style="display:flex; flex-direction: column; align-items: center; width: 100%;">
                <h1> Что-то пошло не так! </h1>
                <h1 class="error"> Error ${statusCode}</h1>
                <h2> Наши инженеры работают над этим </h2>
                <h1><a href="/">Перейти на домашнюю страницу</a></h1>
            </div>
            <div style="display:flex; flex-direction: column; width: 100%;">
                <div><h4>Ошибка произошла по адресу: <a href="${urlBase}${uri}">${urlBase}${uri}</a></h4></div>
                <div><h3>Подробнее: </h3>${message}</div>
            </div>
        </div>

    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>
</html>
