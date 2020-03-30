<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<footer>
    <div class="header-wrap">
        <div class="footer__log">
            <c:choose>
                <c:when test="${isAdmin}">
                    <a class="login" href="${pageContext.request.contextPath}/logout">
                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                        &nbsp;Выйти</a>
                </c:when>
                <c:otherwise>
                    <a class="login" href="${pageContext.request.contextPath}/login">
                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                        &nbsp;Войти</a>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="footer__text">
            &copy; Официальный сайт гостиницы «Комсомолка», 2020
        </div>
    </div>
</footer>
</body>
</html>
