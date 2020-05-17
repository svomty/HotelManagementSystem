<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 1:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/js/btn-active.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="tableTopBar">
    <div>
        <form:form action="${createURL}add" modelAttribute="view" method="get">
            <form:hidden path="page" class="custom-control-input" id="page"/>
            <form:hidden path="size" class="custom-control-input" id="size"/>
            <form:hidden path="sort" class="custom-control-input" id="sort"/>
            <button class="btn-green" type="submit">Добавить новую запись</button>
        </form:form>
    </div>
    <div class="right">
        <label for="page_size">Количество элементов на странице:</label>
        <input size="3" value="${size}" type="number" id="page_size" name="page_size" min="1"
               onsubmit="resizePage('${pageContext.request.contextPath}?sort=${sort}')">
        <button type="button" class="btn-blue"
                onclick=resizePage('${pageContext.request.contextPath}?sort=${sort}')>
            Применить
        </button>
    </div>
</div>
</body>
</html>
