<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 0:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header class="header">
    <div class="header-wrap">
        <nav class="header-active">
            <h1>Гостиница "Комсомолка"</h1>
            <ul>
                <li class="header__itm"><a href="${pageContext.request.contextPath}/" class="btn">Об отеле</a></li>
                <li class="header__itm"><a href="${pageContext.request.contextPath}/apartment/" class="btn">Номера и
                    цены</a></li>
                <li class="header__itm"><a href="${pageContext.request.contextPath}/reservation/" class="btn-red">Бронирование</a>
                </li>
                <c:if test="${isAdmin}">
                    <li class="header__itm" id="admin_panel"><a href="javascript:openSubMenu()" class="btn">Панель
                        администратора
                        <i class="fa fa-caret-down" aria-hidden="true"></i>
                    </a>
                        <ul class="submenu">
                            <li><a href="${pageContext.request.contextPath}/admin/status/" class="submenu__item btn">Статус
                                гостиницы</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/apartment/"
                                   class="submenu__item btn">Апартаменты</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/apartment/price/list/"
                                   class="submenu__item btn">Цены</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/reservation/"
                                   class="submenu__item btn">Система онлайн бронирования</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/customer/" class="submenu__item btn">Клиенты</a>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/admin/accommodation/"
                                   class="submenu__item btn">Заселение</a>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/admin/report/" class="submenu__item btn">Отчетность</a>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/admin/config/" class="submenu__item btn">Конфигурация
                                главного меню</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</header>
</body>
</html>
