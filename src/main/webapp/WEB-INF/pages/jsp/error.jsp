<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Произошла ошибка</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
</head>
<body>
<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>
        <div class="header-wrap" style="flex-direction: column; align-items: center">
            <h1> Что-то пошло не так! </h1>
            <h2> Наши инженеры работают над этим </h2>
            <h1><a href="/">Перейти на домашнюю страницу</a></h1>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>
</html>
