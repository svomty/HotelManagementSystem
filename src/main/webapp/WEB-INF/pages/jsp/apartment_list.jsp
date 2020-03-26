<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 24.03.2020
  Time: 2:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Цены на апартаменты</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>

<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

<div class="content">
    <div class="top-menu">
        <div class="header-wrap">
            <a href="javascript:openMenu()" class="nav-tgl"><img class="nav-img"
                                                                 src="${pageContext.request.contextPath}/img/menu.png"></a>
            <div id="logo"><a href="/">Гостиница "Комсомолка"</a></div>
            <div id="top-menu__nav">
                <ul>
                    <li class="top-menu__itm"><a href="tel:80291233300"><i class="fa fa-phone"
                                                                           aria-hidden="true"></i>&nbsp;+375 29
                        12-33-300</a></li>
                    <li class="top-menu__itm"><a href="/contacts/"><i class="fa fa-map-marker"
                                                                      aria-hidden="true"></i>&nbsp;г.Могилев, ул.
                        Комсомольская, 10</a></li>
                    <li class="top-menu__itm"><a href="mailto:best-hotel@mail.ru"><i class="fa fa-envelope-o"
                                                                                     aria-hidden="true">

                    </i>&nbsp;best-hotel<small>@mail.ru</small></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <header class="header">
        <div class="header-wrap">
            <nav class="header-active">
                <h1>Гостиница "Комсомолка"</h1>
                <ul>
                    <li class="header__itm"><a href="/" class="btn">Об отеле</a></li>
                    <li class="header__itm"><a href="#" class="btn">Номера и цены</a></li>
                    <li class="header__itm"><a href="#" class="btn-red">Бронирование</a></li>
                    <c:if test="${isAdmin}">
                        <li class="header__itm" id="admin_panel"><a href="javascript:openSubMenu()" class="btn">Панель
                            администратора
                            <i class="fa fa-caret-down" aria-hidden="true"></i>
                        </a>
                            <ul class="submenu">
                                <li><a href="#" class="submenu__item btn">Статус гостиницы</a></li>
                                <li><a href="/admin/apartment/" class="submenu__item btn">Апартаменты</a></li>
                                <li><a href="#" class="submenu__item btn">Цены</a></li>
                                <li><a href="#" class="submenu__item btn">Система онлайн бронирования</a></li>
                                <li><a href="#" class="submenu__item btn">Клиенты</a></li>
                                <li><a href="#" class="submenu__item btn">Заселение</a></li>
                                <li><a href="#" class="submenu__item btn">Отчетность</a></li>
                                <li><a href="#" class="submenu__item btn">Конфигурация главного меню</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="header-wrap">
            <div class="divTable" style="border: 1px solid #000;">
                <div class="divTableHeading">
                    <div class="divTableRow">
                        <div class="divTableCell">
                            <a id="id" href="${path}?page=1&sort=id">id</a>
                        </div>
                        <div class="divTableCell">
                            <a id="price" href="${path}?page=1&sort=price">Стоимость места</a>
                        </div>
                        <div class="divTableCell">
                            <a id="rooms_number" href="${path}?page=1&sort=rooms_number">Кол-во комнат</a>
                        </div>
                        <div class="divTableCell">
                            <a id="places_number" href="${path}?page=1&sort=places_number">Кол-во мест</a>
                        </div>
                        <div class="divTableCell">
                            <a id="name_type" href="${path}?page=1&sort=name_type">Тип номера</a>
                        </div>
                        <div class="divTableCell">
                            <a id="description" href="${path}?page=1&sort=description">Описание</a>
                        </div>
                        <div class="divTableCell">Изменить</div>
                        <div class="divTableCell">Удалить</div>
                    </div>
                </div>
                <c:forEach items="${apartment_list}" var="apartment">
                    <div class="divTableBody">
                        <div class="divTableRow">
                            <div class="divTableCell">${apartment.id}</div>
                            <div class="divTableCell">${apartment.price }</div>
                            <div class="divTableCell">${apartment.rooms_number}</div>
                            <div class="divTableCell">${apartment.places_number }</div>
                            <div class="divTableCell">${apartment.type }</div>
                            <div class="divTableCell">${apartment.description }</div>
                            <div class="divTableCell">
                                <spring:url value="/admin/apartment/update/${apartment.id }" var="updateURL"/>
                                <a class="btn btn-primary" href="${updateURL }" role="button">
                                    <i class="fa fa-pencil-square" aria-hidden="true"></i>
                                </a>
                            </div>
                            <div class="divTableCell">
                                <spring:url value="/admin/apartment/delete/${apartment.id }" var="deleteURL"/>
                                <a class="btn btn-primary" href="${deleteURL }" role="button">
                                    <i class="fa fa-trash-o" aria-hidden="true"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${total_page != 1}">
                <div class="pagination">
                    <c:if test="${current_page != 1}">
                        <a href="${path}?page=${current_page-1}&sort=${sort}">«</a>
                        <a href="${path}?page=1&sort=${sort}">1</a>
                    </c:if>
                    <c:if test="${current_page == 1}">
                        <a class="inactive">«</a>
                        <a class="active">1</a>
                    </c:if>

                    <c:if test="${total_page >= 3}">

                        <c:if test="${current_page - 2 > 2}">
                            <a href="#">..</a>
                        </c:if>

                        <c:forEach begin="${start_page}" end="${current_page + 2}" var="p">
                            <c:choose>
                                <c:when test="${current_page == p && p > 1 && p < total_page}">
                                    <a class="active">${p}</a>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${p > 1 && p < total_page}">
                                        <a href="${path}?page=${p}&sort=${sort}">${p}</a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${current_page + 2 < total_page - 1}">
                            <a href="#">..</a>
                        </c:if>

                    </c:if>

                    <c:if test="${current_page != total_page}">
                        <a href="${path}?page=${total_page}&sort=${sort}">${total_page}</a>
                        <a href="${path}?page=${current_page + 1}&sort=${sort}">»</a>
                    </c:if>
                    <c:if test="${current_page == total_page}">
                        <a class="active">${total_page}</a>
                        <a class="inactive">»</a>
                    </c:if>
                </div>
            </c:if>

        </div>
    </main>
</div>


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
<script>
    btnActive("${sort}");
</script>
</html>