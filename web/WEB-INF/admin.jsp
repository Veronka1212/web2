<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>

<head style="text-align: center">
    <%@include file="header.jsp" %>
    <title><fmt:message key="page.admin.head"/></title>
</head>
<body>
<h1 style="text-align: center">
    <div style="color: #c2dbcd"><fmt:message key="page.admin.hello"/></div>
</h1>
<h1>
    <div style="color: #c2dbcd"><fmt:message key="page.admin.pendings"/></div>
</h1>
<style>
    table {
        width: 80%; /* Ширина таблицы */
        background: rgba(234, 255, 235, 0.2); /* Цвет фона таблицы */
        color: #5a6d61; /* Цвет текста */
        border-spacing: 1px; /* Расстояние между ячейками */
    }

    td, th {
        background: rgba(234, 255, 235, 0.4); /* Цвет фона ячеек */
        padding: 5px; /* Поля вокруг текста */
    }
</style>
<table>
    <tr>
        <td>
            <table>
                <c:if test="${empty requestScope.applications}">
                    <fmt:setBundle basename="translations"/>
                    <tr>
                        <td><fmt:message key="page.admin.nopending"/></td>
                    </tr>
                </c:if>
                <c:forEach var="application" items="${requestScope.applications}">
                    <tr>
                        <td><fmt:message key="page.admin.application"/> ${application.id}</td>
                        <td vertical-align: center;>
                            <form action="${pageContext.request.contextPath}/pending" method="get">
                                <br>
                                <input type="hidden" name="command" value="pending">
                                <input type="hidden" name="id" value="${application.id}">
                                    ${application.email}
                                <button type="submit" style="background: lightcyan"><fmt:message
                                        key="page.processing.process"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
        <td style="text-align:center; vertical-align:top;">
            <table>
                <tr>
                    <td>
                        <h3 for="checkout" style="color: #5a6d61"><fmt:message key="page.admin.checkout"/></h3>
                        <c:forEach var="checkouts" items="${requestScope.checkouts}">
                        <option><fmt:message key="page.bills.apartament"/> № ${checkouts.room}
                        </option>
                        <form action="${pageContext.request.contextPath}/evict" method="post">
                            <input type="hidden" name="id" value="${checkouts.id}">
                            <input type="hidden" name="room" value="${checkouts.room}">
                            <input type="hidden" name="command" value="evict">
                            <button type="submit" style="background: lightcyan"><fmt:message
                                    key="page.admin.checkout.button"/></button>
                        </form>
                        </c:forEach>
                        <c:if test="${empty requestScope.checkouts}">
                <tr>
                    <td><fmt:message key="page.admin.nochekouts"/></td>
                </tr>
                </c:if>
                </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
