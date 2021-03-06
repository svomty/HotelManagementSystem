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
    <spring:url value="${urlBase}${urlReturn}add" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

    <c:set var="total_page" value="${total_page}" scope="request"/>
    <c:set var="current_page" value="${current_page}" scope="request"/>
    <c:set var="sort" value="${sort}" scope="request"/>
    <c:set var="size" value="${size}" scope="request"/>
    <c:set var="start_page" value="${start_page}" scope="request"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="/admin/reservation/"/>
    <c:set var="fio" value="${fio}" scope="request"/>
    <c:set var="date" value="${date}" scope="request"/>
    <c:set var="phone" value="${phone}" scope="request"/>
</head>
<body>
<div class="content">
    <jsp:include page="../static/top-menu.jsp"/>
    <jsp:include page="../static/header.jsp"/>
    <main>
        <jsp:include page="../static/popupbg.jsp"/>
        <div class="header-wrap">
            <jsp:include page="../static/tableTopBar.jsp"/>
            <form style="display: none;" action="${urlBase}${urlReturn}" class="poisk-filter" method="get">
                <div style="display: flex; justify-content: right; width: 100%;">
                    <div>
                        <input name="page" id="page" value="1" hidden>
                        <input name="sort" id="sort" value="${sort}" hidden>
                        <input name="size" id="size" value="${size}" hidden>

                        <lable for="fio">ФИО</lable>
                        <input name="fio" id="fio" value="${fio}">

                        <lable for="date">Дата приезда</lable>
                        <input type="date" name="date" id="date" value="${date}">

                        <lable for="phone">Номер телефона</lable>
                        <input name="phone" id="phone" value="${phone}">
                    </div>

                    <input style="width: 24px; height: 24px; padding-left: 1em;" type="image"
                           src="${pageContext.request.contextPath}/img/poisk.png">

                    <a class="btn-discharge"
                       style="margin-left: 1em; padding-left: 5px; padding-right: 5px; padding-top: 2px;"
                       href="${urlBase}${urlReturn}">Сброс</a>
                </div>
            </form>

            <c:if test="${!empty reservation_list}">

                <div class="divTable" style="border: 1px solid #000;">
                    <div class="divTableHeading">
                        <div class="divTableRow">
                            <div class="divTableCell">
                                <a id="id"
                                   href="${pageContext.request.contextPath}?page=1&sort=id&size=${size}&fio=${fio}&date=${date}&phone=${phone}">id</a>
                            </div>
                            <div class="divTableCell">
                                <a id="arrival_date"
                                   href="${pageContext.request.contextPath}?page=1&sort=arrival_date&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
                                    Дата заселения</a>
                            </div>
                            <div class="divTableCell">
                                <a id="departure_date"
                                   href="${pageContext.request.contextPath}?page=1&sort=departure_date&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
                                    Дата выезда
                                </a>
                            </div>
                            <div class="divTableCell">
                                <a id="full_name"
                                   href="${pageContext.request.contextPath}?page=1&sort=full_name&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
                                    ФИО
                                </a>
                            </div>
                            <div class="divTableCell">
                                <a id="customer_phone"
                                   href="${pageContext.request.contextPath}?page=1&sort=customer_phone&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
                                    Телефон для связи
                                </a>
                            </div>
                            <div class="divTableCell">
                                <a id="number"
                                   href="${pageContext.request.contextPath}?page=1&sort=number&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
                                    Номер апартамента
                                </a>
                            </div>
                            <div class="divTableCell">
                                <a id="arrived"
                                   href="${pageContext.request.contextPath}?page=1&sort=arrived&size=${size}&fio=${fio}&date=${date}&phone=${phone}">
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
                                    <div class="flex-two-icon">
                                        <spring:url
                                                value="${urlBase}${urlReturn}update/${reservation.id }?page=${current_page}&size=${size}&sort=${sort}"
                                                var="updateURL"/>
                                        <a class="btn btn-primary" href="${updateURL }" role="button">
                                            <i style="color: #E2B231" class="fa fa-pencil-square" aria-hidden="true"></i>
                                        </a>
                                        <spring:url
                                                value="${urlBase}/admin/accommodation/add?arrival_date_filter=${reservation.arrival_date }&departure_date_filter=${reservation.departure_date}&apartment=${apartments[loop.index].id}&reservation=${reservation.id}&full_name=${reservation.full_name}"
                                                var="finishURL"/>
                                        <a class="btn btn-primary" href="${finishURL }" role="button">
                                            <i style="color: #5197E6" class="fa fa-child" aria-hidden="true"></i>
                                        </a>
                                    </div>
                                    <div class="flex-two-icon">
                                        <spring:url
                                                value="${urlBase}/reports/reservation_report/${reservation.UUID}"
                                                var="reportURL"/>
                                        <a class="btn btn-primary" href="${reportURL }" role="button">
                                            <i style="color: #8B8D8C" class="fa fa-file-text-o" aria-hidden="true"></i>
                                        </a>
                                        <spring:url
                                                value="${urlBase}${urlReturn}delete/${reservation.id }?page=${current_page}&size=${size}&sort=${sort}&fio=${fio}&date=${date}&phone=${phone}"
                                                var="deleteURL"/>
                                        <a class="btn btn-primary" href="${deleteURL}" role="button">
                                            <i style="color: #E22C2F" class="fa fa-trash-o" aria-hidden="true"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <jsp:include page="../static/bottomBar.jsp"/>
            </c:if>
            <c:if test="${empty reservation_list}">

                <div style="display: block; flex-direction: column;">
                    <h1> Ничего не найдено :( </h1>
                    <a class="btn-discharge error" href="${urlBase}${urlReturn}">Сбросить фильтры</a>
                </div>

            </c:if>
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
