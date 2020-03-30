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

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/apartment/price/"/>
    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>

</head>

<body>

<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
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
                            <a href="#">г.Могилев, ул. Комсомольская, 10</a>
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
<jsp:include page="static/footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/js/slider.js"></script>
</html>