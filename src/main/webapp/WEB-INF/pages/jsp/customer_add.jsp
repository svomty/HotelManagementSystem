<%--
  Created by IntelliJ IDEA.
  User: Artyom
  Date: 05.04.2020
  Time: 12:53
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <c:set var="urlBase"
           value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
    <c:url var="urlReturn" value="/admin/customer/"/>
    <spring:url value="${urlBase}${urlReturn}add/" var="createURL"/>

    <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
    <c:set var="isAdmin" value="${isAdmin}" scope="request"/>
    <c:set var="createURL" value="${createURL}" scope="request"/>

</head>
<body>

<div class="content">
    <jsp:include page="static/top-menu.jsp"/>
    <jsp:include page="static/header.jsp"/>
    <main>
        <jsp:include page="static/popupbg.jsp"/>
        <div class="header-wrap">
            <div>
                <h2>Добавление клиента</h2>

                <form:form modelAttribute="customer" method="post" action="${createURL}" cssClass="form">

                    <div class="flex50">
                        <div class="column">
                            <form:hidden path="id"/>
                            <lable for="surname">Фамилия</lable>
                            <form:input path="surname" cssClass="form-control" id="surname"/>

                            <lable for="name">Имя</lable>
                            <form:input path="name" cssClass="form-control" id="name"/>

                            <lable for="patronymic">Отчество</lable>
                            <form:input path="patronymic" cssClass="form-control" id="patronymic"/>

                            <lable for="birth_date">Дата рождения</lable>
                            <form:input path="birth_date" cssClass="form-control" id="birth_date"/>

                            <lable for="passport_serial_number">Серийный номер паспорта</lable>
                            <form:input path="passport_serial_number" cssClass="form-control"
                                        id="passport_serial_number"/>

                            <lable for="identification_number">Идентификационный номер</lable>
                            <form:input path="identification_number" cssClass="form-control"
                                        id="identification_number"/>

                            <lable for="date_issue_passport">Дата выдачи паспорта</lable>
                            <form:input path="date_issue_passport" cssClass="form-control"
                                        id="date_issue_passport"/>

                            <lable for="issuing_authority">Кем выдан</lable>
                            <form:input path="issuing_authority" cssClass="form-control" id="issuing_authority"/>


                            <lable for="registration_address">Регистрационный адрес</lable>
                            <form:input path="registration_address" cssClass="form-control"
                                        id="registration_address"/>
                        </div>
                            <div class="column">
                                <form:form modelAttribute="checker" method="post" action="${createURL}"
                                           cssClass="form">

                                    <div class="custom-control custom-checkbox">
                                        <form:checkbox path="check" class="custom-control-input" id="check"/>
                                        <label class="custom-control-label" for="check">Иностранец</label>
                                    </div><div></div>

                                    <form:form modelAttribute="foreignCustomer" method="post" action="${createURL}"
                                               cssClass="form">
                                        <lable for="customer_id">customer_id</lable>
                                        <form:input path="customer_id" cssClass="form-control" id="customer_id"/>

                                        <lable for="date_entry_to_Belarus">Дата приезда в РБ</lable>
                                        <form:input path="date_entry_to_Belarus" cssClass="form-control"
                                                    id="date_entry_to_Belarus"/>

                                        <lable for="insurance_policy_number">Номер страхового полиса</lable>
                                        <form:input path="insurance_policy_number" cssClass="form-control"
                                                    id="insurance_policy_number"/>

                                        <lable for="visa_number">Номер визы</lable>
                                        <form:input path="visa_number" cssClass="form-control" id="visa_number"/>

                                        <lable for="passport_validity_date">Срок действия паспорта</lable>
                                        <form:input path="passport_validity_date" cssClass="form-control"
                                                    id="passport_validity_date"/>

                                        <lable for="citizenship">Гражданство</lable>
                                        <form:input path="citizenship" cssClass="form-control" id="citizenship"/>

                                        <lable for="insurance_policy_issue_date">Дата выдачи страхового полиса</lable>
                                        <form:input path="insurance_policy_issue_date" cssClass="form-control"
                                                    id="insurance_policy_issue_date"/>

                                        <lable for="insurance_policy_validity">Срок действия страхового полиса</lable>
                                        <form:input path="insurance_policy_validity" cssClass="form-control"
                                                    id="insurance_policy_validity"/>

                                        <button type="submit" class="btn btn-primary">Сохранить</button>
                                    </form:form>
                                </form:form>
                            </div>
                    </div>

                </form:form>
            </div>
        </div>
    </main>
</div>
<jsp:include page="static/footer.jsp"/>
</body>
</html>