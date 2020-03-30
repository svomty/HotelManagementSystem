<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 31.03.2020
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>
</head>
<body>
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
</body>
</html>
