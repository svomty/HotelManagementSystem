<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Система онлайн бронирования</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/reservation/"/>
    <spring:url value="${urlBase}${urlReturn}add/" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

    <c:set var="total_page" value="${total_page}" scope="request"/>
    <c:set var="current_page" value="${current_page}" scope="request"/>
    <c:set var="sort" value="${sort}" scope="request"/>
    <c:set var="size" value="${size}" scope="request"/>
    <c:set var="start_page" value="${start_page}" scope="request"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="${urlReturn}add" scope="request"/>

</head>
<body>
<div class="content">
    <jsp:include page="../static/top-menu.jsp"/>
    <jsp:include page="../static/header.jsp"/>
    <main>
        <jsp:include page="../static/popupbg.jsp"/>
        <div class="header-wrap">
            <jsp:include page="../static/tableTopBar.jsp"/>
            <div class="divTable" style="border: 1px solid #000;">
                <div class="divTableHeading">
                    <div class="divTableRow">
                        <div class="divTableCell">
                            <a id="id" href="${pageContext.request.contextPath}?page=1&sort=id&size=${size}">id</a>
                        </div>
                        <div class="divTableCell">
                            <a id="arrival_date"
                               href="${pageContext.request.contextPath}?page=1&sort=arrival_date&size=${size}">
                                Дата заселения</a>
                        </div>
                        <div class="divTableCell">
                            <a id="departure_date"
                               href="${pageContext.request.contextPath}?page=1&sort=departure_date&size=${size}">
                                Дата выезда
                            </a>
                        </div>
                        <div class="divTableCell">
                            <a id="full_name"
                               href="${pageContext.request.contextPath}?page=1&sort=full_name&size=${size}">
                                ФИО
                            </a>
                        </div>
                        <div class="divTableCell">
                            <a id="customer_phone"
                               href="${pageContext.request.contextPath}?page=1&sort=customer_phone&size=${size}">
                                Телефон для связи
                            </a>
                        </div>
                        <div class="divTableCell">
                            <a id="number"
                               href="${pageContext.request.contextPath}?page=1&sort=number&size=${size}">
                                Номер апартамента
                            </a>
                        </div>
                        <div class="divTableCell">
                            <a id="arrived"
                               href="${pageContext.request.contextPath}?page=1&sort=arrived&size=${size}">
                                Подтверждено
                            </a>
                        </div>
                        <div class="divTableCell">Опции</div>
                    </div>
                </div>
                <c:forEach items="${reservation_list}" var="reservation" varStatus="loop">
                    <div class="divTableBody">
                        <div class="divTableRow">
                            <div class="divTableCell">${reservation.id}</div>
                            <div class="divTableCell">${reservation.arrival_date }</div>
                            <div class="divTableCell">${reservation.departure_date }</div>
                            <div class="divTableCell">${reservation.full_name}</div>
                            <div class="divTableCell">${reservation.customer_phone}</div>
                            <div class="divTableCell">${apartments[loop.index].number}</div>

                            <div class="divTableCell">
                                <input type='checkbox' name='option1'
                                       value='a1'
                                    <c:out default="None" escapeXml="true"
                                           value="${reservation.arrived == 1 ? 'checked ' : ' '}"/>
                                       readonly disabled>
                            </div>

                            <div class="divTableCell">
                                <spring:url
                                        value="${urlBase}${urlReturn}update/${reservation.id }" var="updateURL"/>
                                <a class="btn btn-primary" href="${updateURL }" role="button">
                                    <i style="color: #E2B231" class="fa fa-pencil-square" aria-hidden="true"></i>
                                </a>
                                <spring:url
                                        value="${urlBase}/admin/accommodation/add?arrival_date_filter=${reservation.arrival_date }&departure_date_filter=${reservation.departure_date}&apartment=${apartments[loop.index].id}&reservation=${reservation.id}&full_name=${reservation.full_name}"
                                        var="finishURL"/>
                                <a class="btn btn-primary" href="${finishURL }" role="button">
                                    <i style="color: #5197E6" class="fa fa-child" aria-hidden="true"></i>
                                </a>

                                <spring:url
                                        value="${urlBase}${urlReturn}delete/${reservation.id }?page=${current_page}&size=${size}&sort=${sort}"
                                        var="deleteURL"/>
                                <a class="btn btn-primary" href="${deleteURL}" role="button">
                                    <i style="color: #E22C2F" class="fa fa-trash-o" aria-hidden="true"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="../static/bottomBar.jsp"/>
        </div>
    </main>
</div>
<jsp:include page="../static/footer.jsp"/>
</body>
<script>
    btnActive("${sort}");
    popup_active("${pageContext.request.contextPath}/admin/reservation/");
</script>
</html>
