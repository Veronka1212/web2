<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<style>
    body {
        background: url(https://images5.alphacoders.com/891/891165.jpg);
    }
</style>
<div style="
text-align: center">
    <head>
        <%@include file="header.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="page.admin.application"/></title>
    </head>

    <form action="${pageContext.request.contextPath}/application" method="post">
        <label for="bed">
            <h3 style="color: #c2dbcd"><fmt:message key="page.application.beds"/></h3>
        </label><select name="bed" id="bed" style="background: azure">
        <c:forEach var="beds" items="${requestScope.beds}">
            <option value="${beds}">${beds}</option>
        </c:forEach>
    </select>
        <label for="type">
            <h3>
                <div style="color: #d0efdf"><fmt:message key="page.application.type"/></div>
            </h3>
        </label><select name="type" id="type" style="background: azure">
        <c:forEach var="types" items="${requestScope.types}">
            <option value="${types}">${types}</option>
        </c:forEach>
    </select>
        <label for="time">
            <h3>
                <div style="color: #e0ffef"><fmt:message key="page.application.time"/></div>
            </h3>
            <input type="number" name="time" id="time" value="1" min="1" max="365" required style="background: azure">
        </label><br><br>
        <button type="submit" style="background: lightcyan"><fmt:message key="page.application.booking"/></button>
        <input type="hidden" name="command" value="booking">
        <c:if test="${not empty requestScope.errors}">
            <br><br>
            <c:forEach var="error" items="${requestScope.errors}">
                <span style="color: fuchsia">${error.message}</span>
                <br><br>
            </c:forEach>
        </c:if>
        <br><br>
    </form>
    <form action="${pageContext.request.contextPath}/login" method="get">
        <button type="submit" style="background: lightcyan"><fmt:message key="page.application.home"/></button>
        <input type="hidden" name="command" value="login">
    </form>
</div>
</html>
