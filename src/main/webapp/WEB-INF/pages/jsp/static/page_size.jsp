<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/js/btn-active.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="flex-right">
    <label for="page_size">Количество элементов на странице:</label>
    <input size="3" value="${size}" type="number" id="page_size" name="page_size" min="1"
           onsubmit="resizePage('${pageContext.request.contextPath}?sort=${sort}&arrival_date_filter=${arrival_date_filter}&departure_date_filter=${departure_date_filter}&fio=${fio}&date=${date}&phone=${phone}')">
    <button type="button" class="btn-blue"
            onclick=resizePage('${pageContext.request.contextPath}?sort=${sort}&arrival_date_filter=${arrival_date_filter}&departure_date_filter=${departure_date_filter}&fio=${fio}&date=${date}&phone=${phone}')>
        Применить
    </button>
</div>
</body>
</html>
