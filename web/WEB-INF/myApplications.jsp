<%--@elvariable id="application" type="entity.Application"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="header.jsp" %>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>
<head>
    <title><fmt:message key="page.successful.profile"/></title>
</head>
<body>
<h1 style="color: #c2dbcd"><fmt:message key="page.home.myapplications"/></h1>
<style>
    table {
        width: 60%; /* Ширина таблицы */
        background: #c2dbcd; /* Цвет фона таблицы */
        color: white; /* Цвет текста */
        border-spacing: 1px; /* Расстояние между ячейками */
    }

    td, th {
        background: #c2dbcd; /* Цвет фона ячеек */
        padding: 5px; /* Поля вокруг текста */
    }
</style>
<c:if test="${empty requestScope.applications}">
    <tr>
        <td><h2 style="color: white"><fmt:message key="page.client.noapplication"/></h2></td>
    </tr>
</c:if>
<c:forEach var="application" items="${requestScope.applications}">
    <table>
        <tr>
            <td><strong><fmt:message key="page.admin.application"/></strong></td>
            <td>${application.id}</td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.index.email"/></strong></td>
            <td>${application.email}</td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.application.beds"/></strong></td>
            <td>${application.bed}</td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.application.type"/></strong></td>
            <td>${application.type}</td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.application.time"/></strong></td>
            <td>${application.time}</td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.bills.status"/></strong></td>
            <td>
                <c:if test="${application.processing_status == 1}">
                    <c:choose>
                        <c:when test="${application.status == false}">
                            <fmt:message key="page.processing.rejected"/>
                        </c:when>
                        <c:when test="${application.status == true}">
                            <fmt:message key="page.processing.confirmed"/>
                        </c:when>
                    </c:choose>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><strong><fmt:message key="page.processing.status"/></strong></td>
            <td>

                <c:choose>
                    <c:when test="${application.processing_status == 0}">
                        <fmt:message key="page.processing.noprocessed"/>
                    </c:when>
                    <c:when test="${application.processing_status == 1}">
                        <fmt:message key="page.processing.processed"/>
                    </c:when>
                </c:choose>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/booking" method="post">
                    <input type="hidden" name="id" value="${application.id}">
                    <input type="hidden" name="command" value="deleteapplication">
                    <button type="submit" style="background: lightcyan"><fmt:message
                            key="page.successful.del"/></button>
                </form>
            </td>
        </tr>
    </table>
    <br>
</c:forEach>
<br>
<form action="${pageContext.request.contextPath}/client" method="get">
    <button type="submit" style="background: lightcyan"><fmt:message key="page.application.home"/></button>
    <input type="hidden" name="command" value="client">
    <input type="button" style="background: lightcyan" onclick="location.reload(); return false;"
           value="<fmt:message key="page.successful.bills"/>"/>
</form>
</body>
</html>
