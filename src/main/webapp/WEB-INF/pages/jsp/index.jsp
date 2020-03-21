<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>АСОИ "Гостиница"</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>

<body>

<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>

<div class="content">
    <div class="top-menu">
        <div class="header-wrap">
            <a href="javascript:openMenu()" class="nav-tgl"><img class="nav-img" src="../img/menu.png"></a>
            <div id="logo"><a href="#">Гостиница "Комсомолка"</a></div>
            <div id="top-menu__nav">
                <ul>
                    <li class="top-menu__itm"><a href="tel:80291233300"><i class="fa fa-phone"
                                                                           aria-hidden="true"></i>&nbsp;+375 29
                        12-33-300</a></li>
                    <li class="top-menu__itm"><a href="/contacts/"><i class="fa fa-map-marker"
                                                                      aria-hidden="true"></i>&nbsp;г.Могилев, ул.
                        Комсомольская, 10</a></li>
                    <li class="top-menu__itm"><a href="mailto:best-hotel@mail.ru"><i class="fa fa-envelope-o"
                                                                                     aria-hidden="true"></i>&nbsp;best-hotel<small>@mail.ru</small></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <header class="header">
        <div class="header-wrap">
            <nav class="active">
                <h1>Гостиница "Комсомолка"</h1>
                <ul>
                    <li class="header__itm"><a href="#" class="btn">Об отеле</a></li>
                    <!--Услуги Контакты Достопримечательности Отзывы-->
                    <li class="header__itm"><a href="#" class="btn">Номера и цены</a></li>
                    <li class="header__itm"><a href="#" class="btn-red">Бронирование</a></li>
                    <c:if test="${isAdmin}">
                        <li class="header__itm" id="admin_panel"><a href="javascript:openSubMenu()" class="btn">Панель
                            администратора
                            <i class="fa fa-caret-down" aria-hidden="true"></i>
                        </a>
                            <ul class="submenu">
                                <li><a href="#" class="submenu__item btn">Изменить информацию о номерах</a></li>
                                <li><a href="#" class="submenu__item btn">Изменить информацию о ценах</a></li>
                                <li><a href="#" class="submenu__item btn">Система онлайн бронирования</a></li>
                                <li><a href="#" class="submenu__item btn">Изменить информацию о клиентах</a></li>
                                <li><a href="#" class="submenu__item btn">Добавить данные о заселении</a></li>
                                <li><a href="#" class="submenu__item btn">Отчетность</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="header-wrap">

            <div id="slider">
                <img src="" alt=""/>
                <div class="tagline"><span></span></div>
                <div class="center">
                    <div class="booking">
                        <form>
                            <fieldset>
                                <legend>Система онлайн-бронирования</legend>
                                <p><label for="arrival">Заезд</label><input type="date" id="arrival"
                                                                            value="2018-05-02" min="2018-05-02"></p>
                                <p><label for="departure">Выезд</label><input type="date" id="departure"
                                                                              value="2018-05-03" min="2018-05-03"></p>
                                <p><label for="days">Дней</label><input type="text" readonly id="days"></p>
                                <p><label for="departure">Гостей</label><select name="guests">
                                    <option selected value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                </select></p>
                                <input type="submit" value="Поиск свободного номера">
                            </fieldset>
                        </form>
                    </div>
                </div>

            </div>
            <div class="contacts">
                <h1 class="headline">Наши контакты</h1>
                <div class="block">
                    <div class="contacts__table">
                        <section id="contacts__phone">
                            <h3><i class="fa fa-phone" aria-hidden="true"></i>&nbsp;Телефон</h3>
                            <a href="tel:80291233300">+375 29 12-33-300</a>
                        </section>
                        <section id="contacts__mail">
                            <h3><i class="fa fa-map-marker" aria-hidden="true"></i>&nbsp;Email</h3>
                            <a href="/contacts/">г.Могилев, ул. Комсомольская, 10</a>
                        </section>
                        <section id="contacts__address">
                            <h3><i class="fa fa-envelope-o" aria-hidden="true"></i>&nbsp;Адрес</h3>
                            <a href="mailto:best-hotel@mail.ru">best-hotel<small>@mail.ru</small></a>
                        </section>
                    </div>
                    <div id="contacts__map">
                        <a href="https://yandex.ru/maps/?um=constructor%3A5751fdac0a0c0aa90cda67d6c4d820ba6bb618eac618d786776a71e1a77e6dbb&amp;source=constructorStatic"
                           target="_blank"><img
                                src="https://api-maps.yandex.ru/services/constructor/1.0/static/?um=constructor%3A5751fdac0a0c0aa90cda67d6c4d820ba6bb618eac618d786776a71e1a77e6dbb&amp;width=500&amp;height=400&amp;lang=ru_RU"
                                alt="" style="border: 0;"/></a>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<footer>
    <div class="header-wrap">
        <div class="footer__log">
            <c:choose>
                <c:when test="${isAdmin}">
                    <a class="login" href="${pageContext.request.contextPath}/logout" target="_blank">
                        <i class="fa fa-sign-in" aria-hidden="true"></i>
                        &nbsp;Выйти</a>
                </c:when>
                <c:otherwise>
                    <a class="login" href="${pageContext.request.contextPath}/login" target="_blank">
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
<script src="${pageContext.request.contextPath}/js/slider.js"></script>
</html>