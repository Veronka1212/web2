<%--@elvariable id="application" type="entity.Application"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="header.jsp" %>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>
<head>
    <title><fmt:message key="page.bills.mybills"/></title>
</head>
<body>
<h1 style="color: #c2dbcd"><fmt:message key="page.bills.mybills"/></h1>
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
<c:forEach var="bill" items="${requestScope.bills}">
    <table>
        <tr>
            <td><strong> <fmt:message key="page.bills.number"/></strong></td>
            <td>${bill.id}</td>
        </tr>
        <tr>
            <td><strong> <fmt:message key="page.bills.apartament"/></strong></td>
            <td>№ ${bill.room}</td>
        </tr>
        <tr>
            <td><strong> <fmt:message key="page.bills.summ"/></strong></td>
            <td>${bill.cost/100} Br</td>
        </tr>
        <tr>
            <td><strong> <fmt:message key="page.bills.status"/></strong></td>
            <td>
                <c:choose>
                    <c:when test="${bill.paymentState == true}">
                        <fmt:message key="page.successful.yespay"/>
                    </c:when>
                    <c:when test="${bill.paymentState == false}">
                        <fmt:message key="page.successful.nopay"/>
                    </c:when>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${bill.paymentState == false}">
                    <form action="${pageContext.request.contextPath}/bills" method="post">
                        <input type="hidden" name="id" value="${bill.id}">
                        <input type="hidden" name="command" value="pay">
                        <input type="hidden" name="room" value="${bill.room}">
                        <button type="submit" style="background: lightcyan"><fmt:message key="page.successful.pay"/></button>
                    </form>
                </c:if>
            </td>
            <td>
                <c:if test="${bill.paymentState == true}">
                    <form action="${pageContext.request.contextPath}/bills" method="post">
                        <input type="hidden" name="id" value="${bill.id}">
                        <input type="hidden" name="command" value="deletebill">
                        <button type="submit" style="background: lightcyan"><fmt:message key="page.successful.del"/></button>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
    <br>
</c:forEach>
<br>
<form action="${pageContext.request.contextPath}/login" method="get">
    <button type="submit" style="background: lightcyan"><fmt:message key="page.application.home"/></button>
    <input type="hidden" name="command" value="login">
    <input type="button" style="background: lightcyan" onclick="location.reload(); return false;" value="<fmt:message key="page.successful.bills"/>"/>
</form>
</body>
</html>

