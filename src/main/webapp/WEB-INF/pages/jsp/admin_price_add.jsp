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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Добавление новой записи</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/btn-active.js"></script>
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
                    <li class="top-menu__itm"><a href="#"><i class="fa fa-map-marker"
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
                    <li class="header__itm"><a href="${pageContext.request.contextPath}/" class="btn">Об отеле</a></li>
                    <li class="header__itm"><a href="${pageContext.request.contextPath}/apartment" class="btn">Номера и цены</a></li>
                    <li class="header__itm"><a href="${pageContext.request.contextPath}/reservation" class="btn-red">Бронирование</a></li>
                    <c:if test="${isAdmin}">
                        <li class="header__itm" id="admin_panel"><a href="javascript:openSubMenu()" class="btn">Панель
                            администратора
                            <i class="fa fa-caret-down" aria-hidden="true"></i>
                        </a>
                            <ul class="submenu">
                                <li><a href="${pageContext.request.contextPath}/admin/status" class="submenu__item btn">Статус гостиницы</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/apartment/"
                                       class="submenu__item btn">Апартаменты</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/apartment/price/list/"
                                       class="submenu__item btn">Цены</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/reservation" class="submenu__item btn">Система онлайн бронирования</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/client" class="submenu__item btn">Клиенты</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/residence" class="submenu__item btn">Заселение</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/report" class="submenu__item btn">Отчетность</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/config" class="submenu__item btn">Конфигурация главного меню</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="popupbg" id="popupbg">
            <div class="answer-popup">
                <span>Панель администратора</span>
                <a href="${pageContext.request.contextPath}/admin/status">Статус гостиницы</a>
                <a href="${pageContext.request.contextPath}/admin/apartment/">Апартаменты</a>
                <a class="answer-popup__active" href="${pageContext.request.contextPath}/admin/apartment/price/list/">Цены</a>
                <a href="${pageContext.request.contextPath}/admin/reservation">Система онлайн бронирования</a>
                <a href="${pageContext.request.contextPath}/admin/client">Клиенты</a>
                <a href="${pageContext.request.contextPath}/admin/residence">Заселение</a>
                <a href="${pageContext.request.contextPath}/admin/config">Конфигурация главного меню</a>
                <span>Отчетность</span>
                <a href="${pageContext.request.contextPath}/admin/report/residence">Отчет о заселении</a>
                <a href="${pageContext.request.contextPath}/admin/report/profit">Отчет о доходности</a>
                <a href="${pageContext.request.contextPath}/admin/report/client">Отчет о клиентах</a>
            </div>
        </div>
        <div class="header-wrap">

            <spring:url value="${pageContext.request.contextPath}/admin/apartment/price/add" var="saveURL"/>
            <div>
                <h2>Создание нового типа апартаментов</h2>
                <form:form modelAttribute="apartmentType" method="post" action="${saveURL}" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <lable for="price">price</lable>
                        <form:input path="price" cssClass="form-control" id="price"/>
                    </div>
                    <div class="form-group">
                        <lable for="rooms_number">rooms_number</lable>
                        <form:input path="rooms_number" cssClass="form-control" id="rooms_number"/>
                    </div>
                    <div class="form-group">
                        <lable for="places_number">places_number</lable>
                        <form:input path="places_number" cssClass="form-control" id="places_number"/>
                    </div>
                    <div class="form-group">
                        <lable for="type">type</lable>
                        <form:input path="type" cssClass="form-control" id="type"/>
                    </div>
                    <div class="form-group">
                        <lable for="description">description</lable>
                        <form:input path="description" cssClass="form-control" id="description"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </form:form>
            </div>

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