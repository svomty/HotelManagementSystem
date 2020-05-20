<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="tableBottomBar">

    <div>
        <form:form action="${createURL}add" modelAttribute="view" method="get">
            <form:hidden path="page" class="custom-control-input" id="page"/>
            <form:hidden path="size" class="custom-control-input" id="size"/>
            <form:hidden path="sort" class="custom-control-input" id="sort"/>
            <button class="btn-green" type="submit">Добавить новую запись</button>
        </form:form>
    </div>

    <c:if test="${total_page != 1}">
        <div class="pagination">
            <c:if test="${current_page != 1}">
                <a href="${pageContext.request.contextPath}?page=${current_page-1}&sort=${sort}&size=${size}&surname_filter=${surname_filter}&fio=${fio}&date=${date}&phone=${phone}">«</a>
                <a href="${pageContext.request.contextPath}?page=1&sort=${sort}&size=${size}&surname_filter=${surname_filter}&fio=${fio}&date=${date}&phone=${phone}">1</a>
            </c:if>
            <c:if test="${current_page == 1}">
                <a class="inactive">«</a>
                <a class="active">1</a>
            </c:if>

            <c:if test="${total_page >= 3}">

                <c:if test="${current_page - 2 > 2}">
                    <div>..</div>
                </c:if>

                <c:forEach begin="${start_page}" end="${current_page + 2}" var="p">
                    <c:choose>
                        <c:when test="${current_page == p && p > 1 && p < total_page}">
                            <a class="active">${p}</a>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${p > 1 && p < total_page}">
                                <a href="${pageContext.request.contextPath}?page=${p}&sort=${sort}&size=${size}&surname_filter=${surname_filter}&fio=${fio}&date=${date}&phone=${phone}">${p}</a>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${current_page + 2 < total_page - 1}">
                    <a href="#">..</a>
                </c:if>

            </c:if>

            <c:if test="${current_page != total_page}">
                <a href="${pageContext.request.contextPath}?page=${total_page}&sort=${sort}&size=${size}&surname_filter=${surname_filter}${total_page}&fio=${fio}&date=${date}&phone=${phone}">${total_page}</a>
                <a href="${pageContext.request.contextPath}?page=${current_page + 1}&sort=${sort}&size=${size}&surname_filter=${surname_filter}&fio=${fio}&date=${date}&phone=${phone}">»</a>
            </c:if>
            <c:if test="${current_page == total_page}">
                <a class="active">${total_page}</a>
                <a class="inactive">»</a>
            </c:if>
        </div>


        <div class="right">
            <span>стр. №</span>
            <input size="3" type="number" id="pageNo" name="pageNo" min="1" max="${total_page}">
            <button type="button" class="btn-blue"
                    onclick=goToPage("${pageContext.request.contextPath}?sort=${sort}&size=${size}&surname_filter=${surname_filter}&fio=${fio}&date=${date}&phone=${phone}")>
                Перейти
            </button>
        </div>

    </c:if>
</div>
</body>
</html>
