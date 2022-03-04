<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>

<div style="text-align: center">
    <head>
        <%@include file="header.jsp" %>
        <title><fmt:message key="page.home.reception"/></title>
        <h1>
            <div style="color: #c2dbcd"><fmt:message key="page.home.reception"/></div>
        </h1>
    </head>

    <style>
        table {
            width: 70%; /* Ширина таблицы */
            background: rgba(234, 255, 235, 0.4); /* Цвет фона таблицы */
            color: #5a6d61; /* Цвет текста */
            border-spacing: 1px; /* Расстояние между ячейками */
        }

        td, th {
            background: rgba(234, 255, 235, 0.4); /* Цвет фона ячеек */
            padding: 5px; /* Поля вокруг текста */
        }
    </style>
    <c:if test="${not empty requestScope.myRooms}">
        <h2>
            <div style="color: #c2dbcd"><fmt:message key="page.home.reception.rooms"/></div>
        </h2>
    </c:if>
    <c:forEach var="myRoom" items="${requestScope.myRooms}">
        <table>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td><strong><fmt:message key="page.bills.apartament"/></strong></td>
                            <td>${myRoom.id}</td>
                        </tr>
                        <tr>
                            <td><strong><fmt:message key="page.application.beds"/></strong></td>
                            <td>${myRoom.bed}</td>
                        </tr>
                        <tr>
                            <td><strong><fmt:message key="page.application.type"/></strong></td>
                            <td>${myRoom.type}</td>
                        </tr>
                    </table>
                </td>
                <td style="width: 30%; padding: 5px; color: #5a6d61;">
                    <c:choose>
                        <c:when test="${myRoom.cleaning == false}">
                            <form action="${pageContext.request.contextPath}/checkout" method="get">
                                <input type="hidden" name="command" value="checkout">
                                <input type="hidden" name="cleaning" value="true">
                                <input type="hidden" name="room" value="${myRoom.id}">
                                <button type="submit" style="background: lightcyan">
                                    <fmt:message key="page.home.reception.checkout"/></button>
                            </form>
                        </c:when>
                        <c:otherwise><fmt:message key="page.client.message"/></c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
        <br>
    </c:forEach>
    <form action="${pageContext.request.contextPath}/application" method="get">
        <input type="hidden" name="command" value="application">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.application.booking"/></button>
    </form>
    <form action="${pageContext.request.contextPath}/profile" method="get">
        <input type="hidden" name="command" value="profile">
        <input type="hidden" name="email" value="${application.email}">
        <a href="${pageContext.request.contextPath}/profile">
            <button type="submit" style="background: lightcyan"><fmt:message key="page.home.myapplications"/></button>
        </a>
    </form>
    <form action="${pageContext.request.contextPath}/bills" method="get">
        <input type="hidden" name="command" value="profile">
        <input type="hidden" name="email" value="${application.email}">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.bills.mybills"/></button>
    </form>
</div>
</html>

