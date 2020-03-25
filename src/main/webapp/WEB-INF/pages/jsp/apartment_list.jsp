<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 24.03.2020
  Time: 2:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="row">id</th>
        <th scope="row">price</th>
        <th scope="row">rooms_number</th>
        <th scope="row">places_number</th>
        <th scope="row">type</th>
        <th scope="row">description</th>
        <th scope="row">Edit</th>
        <th scope="row">Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${apartment_list}" var="apartment">
        <tr>
            <td>${apartment.id}</td>
            <td>${apartment.price }</td>
            <td>${apartment.rooms_number }</td>
            <td>${apartment.places_number }</td>
            <td>${apartment.type }</td>
            <td>${apartment.description }</td>
            <td>
                <spring:url value="/confidential/apartment/update/${apartment.id }" var="updateURL"/>
                <a class="btn btn-primary" href="${updateURL }" role="button">Update</a>
            </td>
            <td>
                <spring:url value="/confidential/apartment/delete/${apartment.id }" var="deleteURL"/>
                <a class="btn btn-primary" href="${deleteURL }" role="button">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:forEach begin="1" end="${total_page}" var="p">
    <c:choose>
    <c:when test="${current_page.toString() eq p.toString()}">
        <a style="font-size: 120%; color: green">${p}</a>
    </c:when>
    <c:otherwise>
        <a href="${path}?page=${p}">${p}</a>
    </c:otherwise>
    </c:choose>
</c:forEach>
</body>
</html>
